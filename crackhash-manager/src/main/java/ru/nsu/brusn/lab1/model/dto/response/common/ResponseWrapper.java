package ru.nsu.brusn.lab1.model.dto.response.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWrapper<T> {
    private String error;
    private T data;

    public ResponseWrapper (T data) {
        this.data = data;
    }

    private ResponseWrapper(String error, T data) {
        this.error = error;
        this.data = data;
    }

    public static ResponseWrapper<MessageResponse> okMessageResponse(String message) {
        return new ResponseWrapper<>(null, new MessageResponse(message));
    }

    public static ResponseWrapper<MessageResponse> errorMessageResponse(String error) {
        return new ResponseWrapper<>(error, null);
    }
}
