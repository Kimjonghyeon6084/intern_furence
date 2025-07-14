package com.example.assignment.common.exception;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
                HttpStatus.INTERNAL_SERVER_ERROR, "NullPointerException: " + e.getMessage());
    }

    //valid 관련 예외처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("유효성 검사 실패", e);
        return ExceptionResponse.fail(
                HttpStatus.BAD_REQUEST, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException", e);
        return ExceptionResponse.fail(
                HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ExceptionResponse> handleIOException(IOException e) {
        log.error("IOException", e);
        return ExceptionResponse.fail(
                HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    // 모든 나머지 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception e) {
        log.error("Unknown Exception", e);
        return ExceptionResponse.fail(
                HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류: " + e.getMessage());
    }
}
