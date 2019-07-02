package com.epam.rgntest.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DictionaryResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Throwable.class) // Throwable for demonstration only
    protected ResponseEntity<Object> handle(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "Something went wrong, exception message: " + ex.getMessage(),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}

