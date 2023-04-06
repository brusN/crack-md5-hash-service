package ru.nsu.brusn.lab1.service;

import jakarta.xml.bind.DatatypeConverter;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.extern.log4j.Log4j2;
import org.paukov.combinatorics3.Generator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.nsu.brusn.lab1.model.dto.response.CrackHashManagerResponse;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class WorkerService {
    private final MessageDigest md;

    public WorkerService() throws NoSuchAlgorithmException {
        md = MessageDigest.getInstance("MD5");
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

    private boolean isHashBelongsWord(String word, String hash) {
        var wordHash = md.digest(word.getBytes(StandardCharsets.UTF_8));
        var wordStringHash = DatatypeConverter.printHexBinary(wordHash);
        return wordStringHash.equals(hash.toUpperCase());
    }

    private Optional<String> validateHashForWorkerPart(CrackHashManagerRequest request) {
        var hash = request.getHash();
        var alphabet = request.getAlphabet().getSymbols();
        var workPartInfo = getWorkPartInfo(alphabet.size(), request.getPartCount(), request.getPartNumber());

        // Every worker has his own part of the alphabet
        // It's first literal
        for (int i = workPartInfo.startIndex, j = 0; j < workPartInfo.partSize; ++j, ++i) {
            var curLiteral = alphabet.get(i);

            // Check single-literal word
            if (isHashBelongsWord(curLiteral, hash)) {
                return curLiteral.describeConstable();
            }

            // Check multi-literals words
            for (int permutationLength = 1; permutationLength <= request.getMaxLength(); ++permutationLength) {
                for (List<String> permutation : Generator.permutation(alphabet).withRepetitions(permutationLength)) {
                    var word = buildWord(curLiteral, permutation);
                    if (isHashBelongsWord(word, hash)) {
                        return word.describeConstable();
                    }
                }
            }
        }
        return Optional.empty();
    }

    public ResponseEntity<String> crackHash(CrackHashManagerRequest request) {
        var crackHashOptional = validateHashForWorkerPart(request);
        StringWriter writer = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(CrackHashManagerResponse.class);
            Marshaller m = context.createMarshaller();
            m.marshal(new CrackHashManagerResponse(request.getRequestId(), crackHashOptional.orElse(null)), writer);
        } catch (JAXBException e) {
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(writer.toString());
    }
}
