package ru.nsu.brusn.lab1.exception;

public class ManagerApiException extends Exception {
    public ManagerApiException(String message) {
        super(message);
    }

    public ManagerApiException(Throwable cause) {
        super(cause);
    }
}
