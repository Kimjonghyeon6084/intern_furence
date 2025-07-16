package com.example.assignment.common.exception;

import lombok.Getter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * API 예외 발생시 클라이언트에게 반환되는 응답 정보를 담은 DTO.
 * 성공여부, 메세지를 포함.
 * static fail() 을 통해 ResponseEntity 형태로 쉽게 생성 가능
 */
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
