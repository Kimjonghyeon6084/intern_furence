package com.example.assignment.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustom(CustomException ex){
        ErrorCode errorCode = ex.getErrorCode();
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(errorCode.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOther(Exception ex) {
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(
                        ErrorCode.UNKNOWN_ERROR.getCode(),
                        ErrorCode.UNKNOWN_ERROR.getMessage() + " : " + ex.getMessage()));
    }
}
