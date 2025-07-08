package com.example.assignment.domain.dto.user;

import com.example.assignment.domain.validation.ChangeInfoGroup;
import com.example.assignment.domain.validation.LoginGroup;
import com.example.assignment.domain.validation.ResistorGroup;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class LoginReqDto {

    @NotBlank(message = "아이디는 필수로 입력하셔야 합니다.",
                groups = {ResistorGroup.class, LoginGroup.class})
    private final String id;
    @NotBlank(message = "비밀번호는 필수로 입력하셔야 합니다.",
                groups = {ResistorGroup.class, LoginGroup.class, ChangeInfoGroup.class})
    private final String pwd;

}
