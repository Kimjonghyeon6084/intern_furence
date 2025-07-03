package com.example.assignment.common.exception;

public enum ErrorCode {

    INVALID_FILE_EXTENSION("E001", "파일 확장자가 잘못되었습니다."),
    VALIDATION_FAIL("E002", "입력값이 유효하지 않습니다."),
    DB_DUPLICATE("E003", "이미 등록된 값이 있습니다."),
    UNKNOWN_ERROR("E999", "알 수 없는 오류가 발생했습니다.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}
