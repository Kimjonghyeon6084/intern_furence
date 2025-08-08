package com.example.assignment2.domain.dto.user.info;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class UserIdCheckResponseDto {

    private final boolean duplicate;

}
