package com.example.assignment.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ErrorResDto {

    private final int status;
    private final String code;
    private final String message;

}
