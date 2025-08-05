package com.example.assignment2.domain.dto.user.login;

import lombok.Getter;

/**
 * 로그인 결과 메세지를 담는 enum
 */

@Getter
public enum LoginResponseMessage {

    INVALID_ID("아이디가 틀립니다."),
    INVALID_PASSWORD("비밀번호가 틀립니다.");

    private final String message;

    LoginResponseMessage(String message) {
        this.message = message;
    }
}
