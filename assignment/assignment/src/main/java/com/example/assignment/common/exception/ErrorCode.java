package com.example.assignment.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {


    /* 파일 업로드 관련 예외 */
    INVALID_FILE_EXTENSION("U001", "파일 확장자가 .dbflie이 아닙니다."),
    DB_DUPLICATE("U002", "이미 등록된 값이 있습니다."),
    FIELD_COUNT_INVALID("U003", "입력 필드 개수가 올바르지 않습니다."),
    UNKNOWN_ERROR("U999", "알 수 없는 오류가 발생했습니다."),

    /* 파일 형식 관련 예외 */
    ID_NOT_UPPERCASE("F001", "id는 대문자 알파벳만 입력해야 합니다."),
    PWD_NOT_NUMERIC("F002", "pwd는 숫자만 입력해야 합니다."),
    NAME_NOT_KOREAN("F003", "name은 한글만 입력해야 합니다."),
    LEVEL_INVALID("F004", "level은 대문자 A~F 중 하나여야 합니다."),
    REGDATE_INVALID("F005", "reg_date는 yyyy-MM-dd HH:mm:ss 형식이어야 합니다."),

    /* 각 필드 null시 예외체크 */
    ID_EMPTY("F101", "id 값이 입력되지 않았습니다."),
    PWD_EMPTY("F102", "pwd 값이 입력되지 않았습니다."),
    NAME_EMPTY("F103", "name 값이 입력되지 않았습니다."),
    LEVEL_EMPTY("F104", "level 값이 입력되지 않았습니다."),
    REGDATE_EMPTY("F105", "reg_date 값이 입력되지 않았습니다."),
    ;

    private final String status;
    private final String message;

    ErrorCode(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
