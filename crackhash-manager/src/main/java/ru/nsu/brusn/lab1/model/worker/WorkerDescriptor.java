package ru.nsu.brusn.lab1.model.worker;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkerDescriptor {
    private String url;
    private String port;

    public String getAddress() {
        return url + ":" + port;
    }
}
