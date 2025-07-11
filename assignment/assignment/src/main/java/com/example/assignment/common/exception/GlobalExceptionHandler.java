package com.example.assignment.common.exception;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponse> handleNullPointerException(NullPointerException e) {
        log.error("NullPointerException", e);
        return ExceptionResponse.fail(
                HttpStatus.INTERNAL_SERVER_ERROR, "NPE001", "NullPointerException: " + e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException", e);
        return ExceptionResponse.fail(
                HttpStatus.BAD_REQUEST, "ARG001", "잘못된 파라미터: " + e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ExceptionResponse> handleIOException(IOException e) {
        log.error("IOException", e);
        return ExceptionResponse.fail(
                HttpStatus.INTERNAL_SERVER_ERROR, "IO001", "파일 입출력 오류: " + e.getMessage());
    }

    // 모든 나머지 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception e) {
        log.error("Unknown Exception", e);
        return ExceptionResponse.fail(
                HttpStatus.INTERNAL_SERVER_ERROR, "UNDEFINED", "알 수 없는 오류: " + e.getMessage());
    }
}
