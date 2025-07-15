package com.example.assignment.common.exception;

/**
 * 일어나는 런타임 에러 custom으로 예외처리
 */
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode, String message) {
        super(errorCode.getMessage() + (" : " + message));
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() { return errorCode; }
}
