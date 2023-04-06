package ru.nsu.brusn.lab1.model.worker;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.nsu.brusn.lab1.model.task.CrackHashTaskDescriptor;
import ru.nsu.brusn.lab1.model.task.TaskStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkerManager {
    private final List<WorkerDescriptor> workers;
    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;

    private HttpHeaders getHttpHeaders() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        return headers;
    }

    public WorkerManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        httpHeaders = getHttpHeaders();
        workers = Collections.synchronizedList(new ArrayList<>());
    }

    public int getWorkersCount() {
        return workers.size();
    }

    public ResponseEntity<String> sendTaskForWorker(int partNumber, CrackHashTaskDescriptor taskDescriptor, String xmlRequest) throws RestClientException {
        var request = new HttpEntity<>(xmlRequest, httpHeaders);
        taskDescriptor.updateStatus(TaskStatus.IN_PROGRESS);
        return restTemplate.postForEntity("http://" + workers.get(partNumber).getAddress() + "/internal/api/worker/hash/crack/task", request, String.class);
    }
}
