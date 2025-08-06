package com.example.assignment2.domain.dto.user.info;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserSignupResponseDto {

    private final String id;

    private final String name;

}
