package ru.nsu.brusn.lab1.model.task;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import ru.nsu.brusn.lab1.exception.ManagerApiException;
import ru.nsu.brusn.lab1.model.dto.response.task.CrackHashWorkerResponse;
import ru.nsu.brusn.lab1.model.manager.CrackHashManagerRequest;
import ru.nsu.brusn.lab1.model.manager.ObjectFactory;
import ru.nsu.brusn.lab1.model.worker.WorkerManager;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
public class CrackHashTaskManager implements ITaskManager {
    private final Map<UUID, CrackHashTaskDescriptor> tasks;
    private final WorkerManager workerManager;
    private final ObjectFactory objectFactory;
    private final CrackHashManagerRequest.Alphabet alphabet;


    public CrackHashTaskManager(ObjectFactory objectFactory, WorkerManager workerManager) {
        this.objectFactory = objectFactory;
        this.workerManager = workerManager;
        tasks = new ConcurrentHashMap<>();
        alphabet = initAlphabet();
    }

    private CrackHashManagerRequest.Alphabet initAlphabet() {
        var alphabet = objectFactory.createCrackHashManagerRequestAlphabet();
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

    private ResponseEntity<CrackHashWorkerResponse> sendTaskToWorker(UUID uuid, CrackHashTaskDescriptor taskDescriptor, int partNumber) throws ManagerApiException {
        var request = new CrackHashManagerRequest();
        request.setRequestId(uuid.toString());
        request.setHash(taskDescriptor.getHash());
        request.setMaxLength(taskDescriptor.getMaxLength());
        request.setPartCount(workerManager.getWorkersCount());
        request.setPartNumber(partNumber);
        request.setAlphabet(alphabet);

        taskDescriptor.setStatus(TaskStatus.IN_PROGRESS);
        ResponseEntity<CrackHashWorkerResponse> workerResponse;
        try {
            workerResponse = workerManager.sendTaskForWorker(partNumber, taskDescriptor, request);
        } catch (RestClientException e) {
            taskDescriptor.setStatus(TaskStatus.ERROR);
            throw new ManagerApiException("Connection is timeout or error while crack hash on worker side: " + e.getMessage());
        }
        return workerResponse;
    }

    @Override
    public UUID addNewTask(String hash, Integer maxLength) throws ManagerApiException {
        var guid = java.util.UUID.randomUUID();
        var task = new CrackHashTaskDescriptor(hash, maxLength);
        tasks.put(guid, task);
        var cf = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        return sendTaskToWorker(guid, task, 0);
                    } catch (ManagerApiException e) {
                        log.error(e.getMessage());
                        throw new CompletionException(e);
                    }
                });

        try {
            var result = cf.join();
            task.setStatus(TaskStatus.READY);
            task.setData(result.getBody().getData());
        } catch (CompletionException e) {
            log.error(e.getMessage());
            throw new ManagerApiException(e);
        }

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
