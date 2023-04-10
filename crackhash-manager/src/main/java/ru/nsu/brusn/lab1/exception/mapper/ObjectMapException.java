package ru.nsu.brusn.lab1.exception.mapper;

import ru.nsu.brusn.lab1.exception.ManagerApiException;

public class ObjectMapException extends ManagerApiException {
    public ObjectMapException(String message) {
        super(message);
    }
}
