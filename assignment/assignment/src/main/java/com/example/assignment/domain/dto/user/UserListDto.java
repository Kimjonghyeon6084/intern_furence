package com.example.assignment.domain.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Getter
public class UserListDto {

    private final String id;
    private final String name;
    private final String level;
    private final String desc;
    private final Timestamp reg_date;

    public UserListDto(String id, String name, String level, String desc, Timestamp reg_date) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.desc = desc;
        this.reg_date = reg_date;
    }

}
