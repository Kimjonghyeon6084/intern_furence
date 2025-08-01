package com.example.assignment.domain.dto.user;

import lombok.Builder;
import lombok.Getter;

/**
 * 로그인 후 resDTO
 * 로그인 성공, 실패를 나타냄
 * 로그인 실패시 아이디, 비밀번호 중 뭐가 틀렸는지 담는 DTO
 * 로그인 결과 메세지를 나타낼 때는 LoginResultMessage를 담아서 쓸 것.
 */
@Getter
@Builder
public class LoginResponseDto {

    private String id;

    private String name;

    private LoginStatus loginStatus;

    private String loginFailType;

    private String message;

//    private LoginResultMessage loginResultMessage;

}
