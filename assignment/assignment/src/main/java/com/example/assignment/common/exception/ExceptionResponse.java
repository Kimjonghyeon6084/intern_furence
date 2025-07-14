package com.example.assignment.common.exception;

import lombok.Getter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ExceptionResponse {
    private final boolean success;
    private final String message;


    public ExceptionResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static ResponseEntity<ExceptionResponse> fail(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ExceptionResponse(false, message));
    }
}
