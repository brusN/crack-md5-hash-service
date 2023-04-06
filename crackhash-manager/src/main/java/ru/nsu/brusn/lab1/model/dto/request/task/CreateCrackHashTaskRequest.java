package ru.nsu.brusn.lab1.model.dto.request.task;

import lombok.Getter;

@Getter
public class CreateCrackHashTaskRequest {
    private String hash;
    private Integer maxLength;
}
