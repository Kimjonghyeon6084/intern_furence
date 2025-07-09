package com.example.assignment.domain.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class LoginReqDto {

    @NotBlank(message = "아이디는 필수로 입력하셔야 합니다.")
    private final String id;
    @NotBlank(message = "비밀번호는 필수로 입력하셔야 합니다.")
    private final String pwd;

}
