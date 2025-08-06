package com.example.assignment2.domain.dto.user.info;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserEditResponseDto {

    private String id;

    private String pwd;

    private String name;

    private String level;

    private String desc;

    private LocalDateTime modDate;

}
