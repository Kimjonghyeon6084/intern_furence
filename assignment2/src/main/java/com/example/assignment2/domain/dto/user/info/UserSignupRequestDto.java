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

    @NotBlank(message = "아이디는 필수로 입력하셔야 합니다.")
    @Size(min = 4, message = "아이디는 4자리 이상 입력하셔야 합니다.")
    private String id;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[~@#$%^&+=!])(?=\\S+$).{8,15}$",
             message = "비밀번호는 대소문자, 숫자, 특수문자 1개 이상을 포함한 8-15자의 비밀번호를 입력해야 합니다.")
    private String pwd;

    @Pattern(regexp = "^[가-힣]+$", message = "한글만 입력 가능합니다.")
    @Size(min = 2, message = "이름는 2자리 이상 입력하셔야 합니다.")
    private String name;

    @NotBlank(message = "level은 필수로 입력하셔야 합니다.")
    private String level;

    @Column(name = "\"desc\"")
    private String desc;

    private LocalDateTime regDate;

}
