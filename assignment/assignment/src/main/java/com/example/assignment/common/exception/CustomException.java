package com.example.assignment.common.exception;

public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode, String message) {
        super(errorCode.getMessage() + (" : " + message));
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() { return errorCode; }
}
