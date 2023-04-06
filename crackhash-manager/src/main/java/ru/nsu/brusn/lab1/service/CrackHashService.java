package ru.nsu.brusn.lab1.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.nsu.brusn.lab1.exception.ManagerApiException;
import ru.nsu.brusn.lab1.model.dto.request.task.CreateCrackHashTaskRequest;
import ru.nsu.brusn.lab1.model.dto.response.task.CreateCrackHashTaskResponse;
import ru.nsu.brusn.lab1.model.dto.response.task.GetCrackHashTaskStatusResponse;
import ru.nsu.brusn.lab1.model.task.ITaskManager;
import ru.nsu.brusn.lab1.model.task.TaskStatus;

@Service
public class CrackHashService {
    private final ITaskManager taskManager;

    public CrackHashService(ITaskManager taskManager) {
        this.taskManager = taskManager;
    }
    public ResponseEntity<CreateCrackHashTaskResponse> crackHash(CreateCrackHashTaskRequest requestBody) throws ManagerApiException {
        var taskGuid = taskManager.addNewTask(requestBody.getHash(), requestBody.getMaxLength());
        return ResponseEntity.ok(new CreateCrackHashTaskResponse(taskGuid.toString()));
    }

    public ResponseEntity<GetCrackHashTaskStatusResponse> getStatus(String requestId) {
        var taskOptional = taskManager.getTask(requestId);
        if (taskOptional.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new GetCrackHashTaskStatusResponse(TaskStatus.NOT_FOUND, null));
        }
        return ResponseEntity.ok(new GetCrackHashTaskStatusResponse(taskOptional.get()));
    }
}
