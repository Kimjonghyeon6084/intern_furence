package com.example.assignment.domain.dto.user;

import com.example.assignment.domain.valid.ValidationGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginReqDto {

    @NotBlank(message = "아이디는 필수로 입력하셔야 합니다.", groups = ValidationGroup.NotBlankGroup.class)
    @Size(min = 4, message = "아이디는 4자리 이상 입력하셔야 합니다.")
    private String id;
    @NotBlank(message = "비밀번호는 필수로 입력하셔야 합니다.", groups = ValidationGroup.SizeGroup.class)
    @Size(min = 8, message = "비밀번호는 8자리 이상 입력하셔야 합니다.")
    private String pwd;

}
