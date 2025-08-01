package com.example.assignment.domain.dto.user;

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
