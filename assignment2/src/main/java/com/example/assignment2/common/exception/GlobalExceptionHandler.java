package com.example.assignment2.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                HttpStatus.INTERNAL_SERVER_ERROR, "NullPointerException: " + e.getMessage());
    }

    /**
     * valid 예외 관련. 검증 실패 핸들러
     * @valid에 걸렸을 때 id, pwd 둘 중 어디서 생긴건지
     * 그리고 해당 메세지를 담아 프론트로 보낸다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String uri = request.getRequestURI();

        // uri에 따라 예외들을 하나만 보낼지 리스트로 보낼 지 정함.
        // 추가적인 사항들이 더 있을 수도 있으므로 switch 문으로 작성
        switch (uri) {
            case "/login":
                FieldError err = fieldErrors.get(0);
                return ExceptionResponse.fail(HttpStatus.BAD_REQUEST, err.getDefaultMessage(), err.getField());

            default:
                // 기본 전체 에러 리스트 반환
                List<Map<String, String>> defaultErrors = fieldErrors.stream()
                        .map(e -> Map.of("field", e.getField(), "message", e.getDefaultMessage()))
                        .toList();
                String msg = defaultErrors.stream()
                        .map(e -> e.get("message"))
                        .collect(Collectors.joining(", "));
                return ExceptionResponse.fail(HttpStatus.BAD_REQUEST, msg, defaultErrors);
        }
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> CustomExceptionHandler(CustomException e) {
        log.error("CustomException", e);
        return ExceptionResponse.fail(
                HttpStatus.BAD_REQUEST, e.getMessage());
    }

    /**
     * 그 외 모든 예외 처리 핸들러
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception e) {
        log.error("Unknown Exception", e);
        return ExceptionResponse.fail(
                HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
