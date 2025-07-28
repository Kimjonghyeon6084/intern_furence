package com.example.assignment2.domain.dto.user;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Builder
public class UserListResponseDto {

    String id;

    String name;

    String level;

    String desc;

    LocalDateTime regDate;

    @QueryProjection // select 절에 대상을 지정하는 것.
    public UserListResponseDto(String id, String name, String level, String desc, LocalDateTime regDate) {

        this.id = id;

        this.name = name;

        this.level = level;

        this.desc = desc;

        this.regDate = regDate;
    }
}
