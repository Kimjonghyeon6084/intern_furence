package com.example.assignment.domain.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

/**
 * 로그인시 필요한 DTO
 * @NotBlank, @Size를 이용해서 validate함.
 */
@Builder
@Getter
public class LoginRequestDto {

    @NotBlank(message = "아이디는 필수로 입력하셔야 합니다.")
    @Size(min = 4, message = "아이디는 4자리 이상 입력하셔야 합니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수로 입력하셔야 합니다.")
    @Size(min = 8, message = "비밀번호는 8자리 이상 입력하셔야 합니다.")
    private String pwd;

}
