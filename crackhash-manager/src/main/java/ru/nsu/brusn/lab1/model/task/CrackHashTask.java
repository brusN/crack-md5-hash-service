package ru.nsu.brusn.lab1.model.task;

import lombok.Getter;

@Getter
public class CrackHashTask {
    private final String hash;
    private final Integer maxLength;
    private String data;

    public CrackHashTask(String hash, Integer maxLength) {
        this.hash = hash;
        this.maxLength = maxLength;
        data = null;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
