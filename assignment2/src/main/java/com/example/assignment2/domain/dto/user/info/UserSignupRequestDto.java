package com.example.assignment2.domain.dto.user.info;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserSignupRequestDto {

    @NotBlank(message = "아이디 : 필수 정보입니다.")
    @Size(min = 4, message = "아이디: 4자리 이상 입력하셔야 합니다.")
    private String id;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[~@#$%^&+=!])(?=\\S+$).{8,15}$",
             message = "비밀번호: 8-15자의 영문 대/소문자, 숫자, 특수문자를 사용해 주세요.")
    private String pwd;

    @NotBlank(message = "이름: 필수 정보입니다.")
    @Size(min = 2, message = "이름는 2자리 이상 입력하셔야 합니다.")
    @Pattern(regexp = "^[가-힣]+$", message = "한글만 입력 가능합니다.")
    private String name;

    @NotBlank(message = "level: 필수 정보입니다.")
    private String level;

    private String desc;

    private LocalDateTime regDate;

}
