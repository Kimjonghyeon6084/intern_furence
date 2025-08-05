package com.example.assignment2.domain.dto.user.info;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserSignupRequestDto {

    private String id;

    private String pwd;

    private String name;

    private String level;

    @Column(name = "\"desc\"")
    private String desc;

    private LocalDateTime regDate;

}
