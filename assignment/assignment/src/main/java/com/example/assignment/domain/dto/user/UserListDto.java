package com.example.assignment.domain.dto.user;

import lombok.Getter;

import java.sql.Timestamp;

/**
 * pwd 빼고 모든 정보 불러오는 DTO
 */
@Getter
public class UserListDto {

    private String id;
    private String name;
    private String level;
    private String desc;
    private Timestamp reg_date;

    public UserListDto(String id, String name, String level, String desc, Timestamp reg_date) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.desc = desc;
        this.reg_date = reg_date;
    }

}
