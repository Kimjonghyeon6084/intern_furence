package com.example.assignment.domain.dto.user;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class UserListResponseDto {

    String id;

    String name;

    String level;

    String desc;

    Timestamp regDate;

    @QueryProjection
    public UserListResponseDto(String id, String name, String desc, String level, Timestamp regDate) {

        this.id = id;

        this.name = name;

        this.desc = desc;

        this.level = level;

        this.regDate = regDate;
    }
}
