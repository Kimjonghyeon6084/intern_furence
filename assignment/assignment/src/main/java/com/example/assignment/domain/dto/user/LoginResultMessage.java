package com.example.assignment.domain.dto.user;

import lombok.Getter;

@Getter
public enum LoginResultMessage {

    INVALID_ID("아이디가 틀립니다."),
    INVALID_PASSWORD("비밀번호가 틀립니다."),
    SUCCESS("로그인 성공");

    private final String message;

    LoginResultMessage(String message) {
        this.message = message;
    }
}
