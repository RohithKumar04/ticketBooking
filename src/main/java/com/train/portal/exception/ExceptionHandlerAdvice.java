package com.train.portal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value
            = {SeatAlreadyBookedException.class})
    protected ResponseEntity<String> handleConflict(RuntimeException ex) {

        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}
