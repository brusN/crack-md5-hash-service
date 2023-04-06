package ru.nsu.brusn.lab1.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.nsu.brusn.lab1.model.dto.response.common.ResponseWrapper;

@Log4j2
@ControllerAdvice
public class ManagerApiExceptionHandler {
    @ExceptionHandler(value = {ManagerApiException.class})
    public ResponseWrapper<?> handleManagerApiException(ManagerApiException e) {
        log.error(e.getMessage());
        return ResponseWrapper.errorMessageResponse(e.getMessage());
    }
}
