package com.example.assignment.repository;

import com.example.assignment.domain.dto.user.QUserListResponseDto;
import com.example.assignment.domain.dto.user.UserListRequestDto;
import com.example.assignment.domain.dto.user.UserListResponseDto;
import com.example.assignment.domain.entity.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<UserListResponseDto> searchUsers(UserListRequestDto dto, Pageable pageable) {
        QUser user = QUser.user;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (dto.getId() != null && !dto.getId().isBlank()) booleanBuilder.and(user.id.eq(dto.getId()));
        if (dto.getName() != null && !dto.getName().isBlank()) booleanBuilder.and(user.name.eq(dto.getName()));
        if (dto.getLevel() != null && !dto.getLevel().isBlank()) booleanBuilder.and(user.level.eq(dto.getLevel()));
        if (dto.getDesc() != null && !dto.getDesc().isBlank()) booleanBuilder.and(user.desc.contains(dto.getDesc()));
        if (dto.getRegDate() != null) booleanBuilder.and(user.regDate.goe(Timestamp.valueOf(dto.getRegDate().atStartOfDay())));

        System.out.println("검색조건 id: " + dto.getId());
        System.out.println("검색조건 name: " + dto.getName());
        System.out.println("검색조건 level: " + dto.getLevel());
        System.out.println("검색조건 desc: " + dto.getDesc());
        System.out.println("검색조건 regDate: " + dto.getRegDate());

        List<UserListResponseDto> content = jpaQueryFactory
                .select(new QUserListResponseDto(
                        user.id,
                        user.name,
                        user.level,
                        user.desc,
                        user.regDate
                ))
                .from(user)
                .where(booleanBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory
                .select(user.count())
                .from(user)
                .where(booleanBuilder)
                .fetchOne();
        long nullCheckTotal = total != null
                ? total
                : 0L;

        return new PageImpl<>(content, pageable, nullCheckTotal);
    }
}
