package com.train.portal.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value
            = {SeatAlreadyBookedException.class, UserAlreadyExistsException.class, UserNotFoundException.class})
    protected ResponseEntity<String> internalServerError(RuntimeException ex) {

        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

}
