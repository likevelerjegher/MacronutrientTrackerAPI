package com.likevel.kaloriinnhold.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ExceptionErrorMessage> objectNotFoundException(
            final ObjectNotFoundException ex, final WebRequest request) {
        ExceptionErrorMessage message = new ExceptionErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectExistedException.class)
    public ResponseEntity<ExceptionErrorMessage> objectExistedException(
            final ObjectExistedException ex, final WebRequest request) {
        ExceptionErrorMessage message = new ExceptionErrorMessage(
                HttpStatus.CONFLICT.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionErrorMessage> globalExceptionHandler(
            final Exception ex, final WebRequest request) {
        ExceptionErrorMessage message = new ExceptionErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}