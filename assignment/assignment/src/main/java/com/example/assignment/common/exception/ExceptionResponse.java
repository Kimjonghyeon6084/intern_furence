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
    private boolean success;
    private String message;
    private String loginField;


    public ExceptionResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.loginField = null;
    }

    public ExceptionResponse(boolean success, String message, String loginField) {
        this.success = success;
        this.message = message;
        this.loginField = loginField;
    }

    /**
     * 기본 예외 실패 메서드
     * @param status
     * @param message
     * @return ResponseEntity.status(status).body(new ExceptionResponse(false, message))
     */
    public static ResponseEntity<ExceptionResponse> fail(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ExceptionResponse(false, message));
    }

    /**
     * 로그인 시 어떤 필드가 @Valid에 걸렸는지 확인하려는 메서드
     * @param status
     * @param loginField
     * @param message
     * @return ResponseEntity.status(status).body(new ExceptionResponse(false, loginField, message))
     */
    public static ResponseEntity<ExceptionResponse> fail(HttpStatus status, String loginField, String message) {
        return ResponseEntity.status(status).body(new ExceptionResponse(false, loginField, message));
    }
}
