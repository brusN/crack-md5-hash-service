package ru.nsu.brusn.lab1.service;

import jakarta.xml.bind.DatatypeConverter;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.apache.coyote.Request;
import org.paukov.combinatorics3.Generator;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.nsu.brusn.lab1.model.dto.response.CrackHashManagerResponse;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    private final MessageDigest md;

    public WorkerService() throws NoSuchAlgorithmException {
        md =  MessageDigest.getInstance("MD5");
    }


    class WorkPartInfo {
        private int partSize;
        private int startIndex;
    }

    private WorkPartInfo getWorkPartInfo(int alphabetSize, int partCount, int partNumber) {
        int overflowWorkersCount = alphabetSize % partCount;
        int countLiteralsForWorker = alphabetSize / partCount;
        int startIndex = 0;
        var workPartInfo = new WorkPartInfo();

        if (partNumber < overflowWorkersCount) {
            ++countLiteralsForWorker;
            startIndex += countLiteralsForWorker * partNumber;
        } else {
            startIndex += overflowWorkersCount * (countLiteralsForWorker + 1) + (partCount - partNumber - 1) * countLiteralsForWorker;
        }
        workPartInfo.partSize = countLiteralsForWorker;
        workPartInfo.startIndex = startIndex;

        return workPartInfo;
    }

    private String buildWord(String startLiteral, List<String> permutation) {
        var stringBuffer = new StringBuilder(startLiteral);
        permutation.forEach(stringBuffer::append);
        return stringBuffer.toString();
    }

    private Optional<String> tryCrackHash(CrackHashManagerRequest request) {
        var hash = request.getHash();
        var alphabet = request.getAlphabet().getSymbols();
        var workPartInfo = getWorkPartInfo(alphabet.size(), request.getPartCount(), request.getPartNumber());

        for (int i = workPartInfo.startIndex, j = 0; j < workPartInfo.partSize; ++j, ++i) {
            var curLiteral = alphabet.get(i);
            for (int wordLength = 1; wordLength <= request.getMaxLength(); ++wordLength) {
                for (List<String> permutation : Generator.permutation(alphabet).withRepetitions(wordLength)) {
                    var word = buildWord(curLiteral, permutation);
                    var wordHash = md.digest(word.getBytes(StandardCharsets.UTF_8));
                    var wordStringHash = DatatypeConverter.printHexBinary(wordHash);
                    if (wordStringHash.equals(hash.toUpperCase())) {
                        return word.describeConstable();
                    }
                }
            }
        }
        return Optional.empty();
    }

    public ResponseEntity<String> crackHash(CrackHashManagerRequest request) {
        var crackHashOptional = tryCrackHash(request);
        StringWriter writer = new StringWriter();
        // var headers = new HttpHeaders();
        // headers.setContentType(MediaType.APPLICATION_XML);
        if (crackHashOptional.isPresent()) {
            try {
                JAXBContext context = JAXBContext.newInstance(CrackHashManagerResponse.class);
                Marshaller m = context.createMarshaller();
                m.marshal(new CrackHashManagerResponse(request.getRequestId(), crackHashOptional.get()), writer);
            } catch (JAXBException e) {
                System.err.println(e.getMessage());
            }
        }
        // var response = new HttpEntity<>(writer.toString());
        return ResponseEntity.ok(writer.toString());
    }
}
