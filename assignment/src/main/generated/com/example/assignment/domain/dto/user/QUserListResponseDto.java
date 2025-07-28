package com.example.assignment.domain.dto.user;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.assignment.domain.dto.user.QUserListResponseDto is a Querydsl Projection type for UserListResponseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QUserListResponseDto extends ConstructorExpression<UserListResponseDto> {

    private static final long serialVersionUID = -1156941861L;

    public QUserListResponseDto(com.querydsl.core.types.Expression<String> id, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> level, com.querydsl.core.types.Expression<String> desc, com.querydsl.core.types.Expression<? extends java.sql.Timestamp> regDate) {
        super(UserListResponseDto.class, new Class<?>[]{String.class, String.class, String.class, String.class, java.sql.Timestamp.class}, id, name, level, desc, regDate);
    }

}

