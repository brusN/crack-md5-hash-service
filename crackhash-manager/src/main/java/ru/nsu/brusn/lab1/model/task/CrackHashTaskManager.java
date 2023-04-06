package ru.nsu.brusn.lab1.model.task;

import jakarta.xml.bind.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.nsu.brusn.lab1.model.dto.response.CrackHashWorkerResponse;
import ru.nsu.brusn.lab1.model.worker.WorkerDescriptor;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class CrackHashTaskManager implements ITaskManager {
    private final Map<UUID, CrackHashTaskDescriptor> tasks;
    private final List<WorkerDescriptor> workers;
    private final CrackHashManagerRequest.Alphabet alphabet;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public CrackHashTaskManager() {
        tasks = new ConcurrentHashMap<>();
        alphabet = initAlphabet();
        workers = Collections.synchronizedList(new ArrayList<>());
        workers.add(new WorkerDescriptor("localhost", "8080"));
    }

    private CrackHashManagerRequest.Alphabet initAlphabet() {
        var alphabet = new CrackHashManagerRequest.Alphabet();
        var alphabetSymbols = alphabet.getSymbols();
        for (char character = 'a'; character <= 'z'; ++character) {
            alphabetSymbols.add(String.valueOf(character));
        }
        for (char character = 'A'; character <= 'Z'; ++character) {
            alphabetSymbols.add(String.valueOf(character));
        }
        for (char character = '0'; character <= '9'; ++character) {
            alphabetSymbols.add(String.valueOf(character));
        }
        return alphabet;
    }

    private ResponseEntity<String> sendTaskToWorker(UUID uuid, CrackHashTaskDescriptor task, int partNumber) {
        StringWriter writer = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(CrackHashManagerRequest.class);
            Marshaller marshaller = context.createMarshaller();

            var request = new CrackHashManagerRequest();
            request.setRequestId(uuid.toString());
            request.setHash(task.getHash());
            request.setMaxLength(task.getMaxLength());
            request.setPartCount(workers.size());
            request.setPartNumber(partNumber);
            request.setAlphabet(alphabet);

            marshaller.marshal(request, writer);
        } catch (JAXBException e) {
            System.err.println(e.getMessage());
        }
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        var request = new HttpEntity<>(writer.toString(), headers);
        return restTemplate.postForEntity("http://" + workers.get(partNumber).getAddress() + "/internal/api/worker/hash/crack/task", request, String.class);
    }

    @Override
    public UUID addNewTask(String hash, Integer maxLength) {
        var guid = java.util.UUID.randomUUID();
        var task = new CrackHashTaskDescriptor(hash, maxLength);
        tasks.put(guid, task);
        CompletableFuture
                .supplyAsync(() -> sendTaskToWorker(guid, task, 0))
                .thenAccept((result) -> {
                    try {
                        JAXBContext context = JAXBContext.newInstance(CrackHashWorkerResponse.class);
                        Unmarshaller unmarshaller = context.createUnmarshaller();;
                        var element = (CrackHashWorkerResponse) unmarshaller.unmarshal(new StringReader(result.getBody()));
                        task.updateStatus(TaskStatus.READY);
                        task.setData(element.getData());
                    } catch (JAXBException e) {
                        System.err.println(e.getMessage());
                    }
                });
        return guid;
    }

    @Override
    public Optional<CrackHashTaskDescriptor> getTask(String guid) {
        var uuid = UUID.fromString(guid);
        if (tasks.containsKey(uuid)) {
            return Optional.of(tasks.get(uuid));
        }
        return Optional.empty();
    }
}
