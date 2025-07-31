package com.example.assignment2.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * uncheckedException 관련 예외처리 custom
 */
@Getter
public enum ErrorCode {

    /* 파일 업로드 관련 예외 */
    INVALID_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "U001", "파일 확장자가 .dbflie이 아닙니다."),
    DB_DUPLICATE(HttpStatus.CONFLICT, "U002", "이미 등록된 값이 있습니다."),
    FIELD_COUNT_INVALID(HttpStatus.BAD_REQUEST, "U003", "입력 필드 개수가 올바르지 않습니다."),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "U999", "알 수 없는 오류가 발생했습니다."),

    /* 파일 형식 관련 예외 */
    ID_NOT_UPPERCASE(HttpStatus.BAD_REQUEST, "F001", "id는 대문자 알파벳만 입력해야 합니다."),
    PWD_NOT_NUMERIC(HttpStatus.BAD_REQUEST, "F002", "pwd는 숫자만 입력해야 합니다."),
    NAME_NOT_KOREAN(HttpStatus.BAD_REQUEST, "F003", "name은 한글만 입력해야 합니다."),
    LEVEL_INVALID(HttpStatus.BAD_REQUEST, "F004", "level은 대문자 A~F 중 하나여야 합니다."),
    REGDATE_INVALID(HttpStatus.BAD_REQUEST, "F005", "reg_date는 yyyy-MM-dd HH:mm:ss 형식이어야 합니다."),

    /* 각 필드 null시 예외체크 */
    ID_EMPTY(HttpStatus.BAD_REQUEST, "F101", "id 값이 입력되지 않았습니다."),
    PWD_EMPTY(HttpStatus.BAD_REQUEST, "F102", "pwd 값이 입력되지 않았습니다."),
    NAME_EMPTY(HttpStatus.BAD_REQUEST, "F103", "name 값이 입력되지 않았습니다."),
    LEVEL_EMPTY(HttpStatus.BAD_REQUEST, "F104", "level 값이 입력되지 않았습니다."),
    REGDATE_EMPTY(HttpStatus.BAD_REQUEST, "F105", "reg_date 값이 입력되지 않았습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
