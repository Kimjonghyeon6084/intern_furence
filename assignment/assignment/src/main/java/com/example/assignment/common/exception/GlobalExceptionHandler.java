package com.example.assignment.common.exception;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * 발생하는 예외를 global하게 처리하는 클래스.
 * 예외별 을답 메세지와 상태코드 줌.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * nullpoint 예외 관련 핸들러
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponse> handleNullPointerException(NullPointerException e) {
        log.error("NullPointerException", e);
        return ExceptionResponse.fail(
                HttpStatus.BAD_REQUEST, "NullPointerException: " + e.getMessage());
    }

    /**
     * valid 예외 관련. 검증 실패 핸들러
     * @valid에 걸렸을 때 id, pwd 둘 중 어디서 생긴건지
     * 그리고 해당 메세지를 담아 프론트로 보낸다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("로그인 아이디, 비밀번호 검사 실패", e);
//        List<String> message = e.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(FieldError::getDefaultMessage)
//                .toList();
//        return ExceptionResponse.fail(
//                HttpStatus.BAD_REQUEST, String.join("\n", message)

        FieldError fieldError = e.getBindingResult().getFieldError(); // @valid에 걸린 예외
        String field = fieldError.getField(); // id인지 pwd인지 확인
        String msg = fieldError.getDefaultMessage(); // @Valid에 있는 msg
        return ExceptionResponse.fail(
                HttpStatus.BAD_REQUEST, msg, field);
    }

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ExceptionResponse> handleLoginFailedException(LoginFailedException e) {
        log.error("로그인 실패");
        return ExceptionResponse.fail(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return ExceptionResponse.fail(
                HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    /**
     * 그 외 모든 예외 처리 핸들러
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception e) {
        log.error("Unknown Exception", e);
        return ExceptionResponse.fail(
                HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류: " + e.getMessage());
    }
}
