package ru.nsu.brusn.lab1.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.nsu.brusn.lab1.model.dto.request.CreateCrackHashTaskRequest;
import ru.nsu.brusn.lab1.model.dto.response.CreateCrackHashTaskResponse;
import ru.nsu.brusn.lab1.model.task.CrackHashTaskManager;

@Service
public class CrackHashService {
    private final RestTemplate restTemplate;
    private final CrackHashTaskManager taskManager;

    public CrackHashService(RestTemplate restTemplate, CrackHashTaskManager taskManager) {
        this.restTemplate = restTemplate;
        this.taskManager = taskManager;
    }
    public ResponseEntity<Object> crackHash(CreateCrackHashTaskRequest requestBody) {
        var taskGuid = taskManager.addNewTask(requestBody.getHash(), requestBody.getMaxLength());
        return ResponseEntity.ok(new CreateCrackHashTaskResponse(taskGuid));
    }
}
