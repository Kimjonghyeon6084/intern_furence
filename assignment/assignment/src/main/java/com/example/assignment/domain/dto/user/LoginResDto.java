package com.example.assignment.domain.dto.user;

import com.example.assignment.domain.dto.LoginField;
import com.example.assignment.domain.dto.LoginSuccessField;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 로그인 후 resDTO
 * 로그인 성공, 실패를 나타냄
 * 로그인 실패시 아이디, 비밀번호 중 뭐가 틀렸는지 담는 DTO
 */
@Getter
@Builder
public class LoginResDto {

    private String id;

    private String name;

    private LoginSuccessField successField;

    private LoginField loginField;

    private String message;

}
