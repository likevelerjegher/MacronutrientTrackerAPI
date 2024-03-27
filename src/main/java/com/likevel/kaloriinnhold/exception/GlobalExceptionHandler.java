package com.likevel.kaloriinnhold.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.type.descriptor.java.ObjectJavaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<Object> handeHttpClientErrorException(
            final HttpClientErrorException exception, final WebRequest request) {
        LOGGER.error("400 Bad Request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400 Bad Request");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        StringBuilder message = new StringBuilder();
        for (FieldError fieldError : fieldErrors){
            message.append(fieldError.getDefaultMessage()).append("; ");
        }
        LOGGER.error("400 Bad Request: {}", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message.toString());
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Object> handleMethodNotSupportedException(
            final HttpRequestMethodNotSupportedException exception,
            final WebRequest request){
        LOGGER.error("405 Method Not Allowed");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("405 Method Not Allowed");
    }
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(
            final RuntimeException exception, final WebRequest request){
        LOGGER.error("500 Internal Server Error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("500 Internal Server Error");
    }
}
