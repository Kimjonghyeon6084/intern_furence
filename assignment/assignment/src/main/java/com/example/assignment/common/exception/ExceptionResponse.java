package com.example.assignment.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ExceptionResponse {
    private final boolean success;
    private final String code;
    private final String message;


    public ExceptionResponse(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public static ResponseEntity<ExceptionResponse> fail(HttpStatus status, String code, String message) {
        return ResponseEntity.status(status).body(new ExceptionResponse(false, code, message));
    }
}
