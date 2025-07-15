package com.example.assignment.common.exception;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

/**
 * 전체적으로 일어나는 예외처리를 한 곳에서 하기 위한 클래스
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * nullpoint 예외
     * @param e
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponse> handleNullPointerException(NullPointerException e) {
        log.error("NullPointerException", e);
        return ExceptionResponse.fail(
                HttpStatus.INTERNAL_SERVER_ERROR, "NullPointerException: " + e.getMessage());
    }

    /**
     * valid 예외
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("유효성 검사 실패", e);
        return ExceptionResponse.fail(
                HttpStatus.BAD_REQUEST, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    // 모든 나머지 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception e) {
        log.error("Unknown Exception", e);
        return ExceptionResponse.fail(
                HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류: " + e.getMessage());
    }
}
