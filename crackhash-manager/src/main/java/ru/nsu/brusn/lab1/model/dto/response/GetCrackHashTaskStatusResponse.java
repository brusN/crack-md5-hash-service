package ru.nsu.brusn.lab1.model.dto.response;

import ru.nsu.brusn.lab1.model.task.TaskStatus;

import java.util.List;

public class GetCrackHashTaskStatusResponse {
    private TaskStatus status;
    private List<String> data;
}
