package com.example.assignment.common.exception;

import com.example.assignment.domain.dto.file.UploadError;
import com.example.assignment.domain.dto.file.UploadResult;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

// 커스텀 예외처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<UploadResult> handleCustomException(CustomException e) {
        log.warn("👌CustomException 발생: {}", e.getMessage());
        List<UploadError> errors = new ArrayList<>();
        errors.add(new UploadError(0, e.getMessage()));
        return ResponseEntity.ok(new UploadResult(0, errors));
    }

    // 그 이외 예외처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<UploadResult> handleAllExceptions(Exception ex) {
        log.error("Unexpected Exception", ex);
        List<UploadError> errors = new ArrayList<>();
        errors.add(new UploadError(0, "서버 내부 오류: " + ex.getMessage()));
        return ResponseEntity.ok(new UploadResult(0, errors));
    }
}
