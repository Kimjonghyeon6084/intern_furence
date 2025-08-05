package com.example.assignment2.domain.dto.user.login;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {

    private String id;

    private String name;

    private LoginStatus loginStatus;

    private LoginValidField loginValidField;

    private LoginResponseMessage loginResponseMessage;

    private String message;


}
