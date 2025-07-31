package com.example.assignment2.domain.dto.user;

import lombok.Getter;
import lombok.Setter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class UserListRequestDto {

    private String id;

    private String name;

    private String level;

    private String desc;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startRegDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endRegDate;
}
