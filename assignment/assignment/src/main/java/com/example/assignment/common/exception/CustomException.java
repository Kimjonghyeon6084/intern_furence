package com.example.assignment.common.exception;

/**
 * 비즈니스 로직에서 일어나는 예외 상황을 ErrorCode와 함께 처리하기 위한 커스텀 예외 클래스.
 * ErrorCode enum을 활용해 예외 유형 및 메세지 전달.
 */
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode, String message) {
        super(errorCode.getMessage() + (" : " + message));
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() { return errorCode; }
}
