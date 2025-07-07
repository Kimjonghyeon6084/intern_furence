package com.example.assignment.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    //IllegalArgumentException, NullPointerException
    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    public ResponseEntity<ErrorResDto> handleCustomException(RuntimeException e) {
        ErrorResDto res = new ErrorResDto(
                HttpStatus.BAD_REQUEST.value(),
                e.getClass().getName(),
                e.getMessage()
        );
        return ResponseEntity.badRequest().body(res);
    }

    // 그 이외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResDto> handAllException(Exception e) {
        ErrorResDto dto = new ErrorResDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Error",
                "알 수 없는 서버 오류입니다."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
    }
}
