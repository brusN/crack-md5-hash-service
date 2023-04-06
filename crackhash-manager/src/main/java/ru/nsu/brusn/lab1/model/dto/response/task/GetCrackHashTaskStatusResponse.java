package ru.nsu.brusn.lab1.model.dto.response.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.brusn.lab1.model.task.CrackHashTaskDescriptor;
import ru.nsu.brusn.lab1.model.task.TaskStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetCrackHashTaskStatusResponse {
    private TaskStatus status;
    private List<String> data;

    public GetCrackHashTaskStatusResponse(CrackHashTaskDescriptor task) {
        status = task.getStatus();
        var taskData = task.getData();
        data = taskData == null ? null : List.of(taskData);
    }
}
