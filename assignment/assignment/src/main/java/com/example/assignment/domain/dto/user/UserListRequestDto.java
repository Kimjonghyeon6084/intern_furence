package com.example.assignment.domain.dto.user;

import lombok.Getter;
import lombok.Setter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Setter
@Getter
public class UserListRequestDto {

    private String id;

    private String name;

    private String level;

    private String desc;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate regDate;
}
