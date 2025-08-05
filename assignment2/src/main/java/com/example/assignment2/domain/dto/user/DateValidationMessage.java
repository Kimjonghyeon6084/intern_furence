package com.example.assignment2.domain.dto.user;

import lombok.Getter;

@Getter
public enum DateValidationMessage {

    INVALID_RANGE("시작일이 종료일보다 늦을 수 없습니다."),
    EMPTY_RANGE("시작일과 종료일중 하나만 입력할 순 없습니다.");

    private final String message;

    DateValidationMessage(String message) {
        this.message = message;
    }
}
