package com.example.assignment.domain.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 로그인 후 resDTO
 */
@Getter
@RequiredArgsConstructor
@Builder
public class LoginResDto {

    private final String id;
    private final String pwd;

}
