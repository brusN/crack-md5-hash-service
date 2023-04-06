package ru.nsu.brusn.lab1.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.nsu.brusn.lab1.model.dto.request.CreateCrackHashTaskRequest;
import ru.nsu.brusn.lab1.model.dto.request.CrackHashWorkerRequest;
import ru.nsu.brusn.lab1.model.dto.response.CreateCrackHashTaskResponse;
import ru.nsu.brusn.lab1.model.dto.response.GetCrackHashTaskStatusResponse;
import ru.nsu.brusn.lab1.model.dto.response.MessageResponse;
import ru.nsu.brusn.lab1.model.task.CrackHashTaskManager;
import ru.nsu.brusn.lab1.model.task.TaskStatus;

import java.util.List;

@Service
public class CrackHashService {
    private final CrackHashTaskManager taskManager;

    public CrackHashService(CrackHashTaskManager taskManager) {
        this.taskManager = taskManager;
    }
    public ResponseEntity<CreateCrackHashTaskResponse> crackHash(CreateCrackHashTaskRequest requestBody) {
        var taskGuid = taskManager.addNewTask(requestBody.getHash(), requestBody.getMaxLength());
        return ResponseEntity.ok(new CreateCrackHashTaskResponse(taskGuid.toString()));
    }

    public ResponseEntity<MessageResponse> updateTaskStatus(CrackHashWorkerRequest request) {
        var taskOptional = taskManager.getTask(request.getRequestId());
        if (taskOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Task with guid " + request.getRequestId() + " not found!"));
        }
        var task = taskOptional.get();
        task.updateStatus(TaskStatus.READY);
        task.setData(request.getData());
        return ResponseEntity.ok(new MessageResponse("Task status has updated successfully"));
    }

    public ResponseEntity<GetCrackHashTaskStatusResponse> getStatus(String requestId) {
        var taskOptional = taskManager.getTask(requestId);
        if (taskOptional.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new GetCrackHashTaskStatusResponse(TaskStatus.NOT_FOUND, List.of("Task with guid not found!")));
        }
        return ResponseEntity.ok(new GetCrackHashTaskStatusResponse(taskOptional.get()));
    }
}
