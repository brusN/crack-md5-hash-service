package ru.nsu.brusn.lab1.model.task;

import lombok.Getter;

@Getter
public class CrackHashTaskDescriptor {
    private final String hash;
    private final Integer maxLength;
    private String data;

    private TaskStatus status;

    public CrackHashTaskDescriptor(String hash, Integer maxLength) {
        this.hash = hash;
        this.maxLength = maxLength;
        data = null;
        status = TaskStatus.NOT_STARTED;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void updateStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskStatus getStatus() {
        return status;
    }
}
