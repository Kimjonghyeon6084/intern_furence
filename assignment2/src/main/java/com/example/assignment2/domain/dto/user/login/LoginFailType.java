package com.example.assignment2.domain.dto.user.login;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LoginFailType {

    ID("ID", "아이디가 틀립니다."),
    PWD("PWD", "비밀번호가 틀립니다.");

    private final String field;

    private final String message;
}
