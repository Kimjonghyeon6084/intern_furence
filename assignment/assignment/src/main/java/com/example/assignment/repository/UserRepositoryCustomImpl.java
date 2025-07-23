package com.example.assignment.repository;

import com.example.assignment.domain.dto.user.QUserListResponseDto;
import com.example.assignment.domain.dto.user.UserListRequestDto;
import com.example.assignment.domain.dto.user.UserListResponseDto;
import com.example.assignment.domain.entity.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

/**
 * queryDsl 사용해서 필터링된 사용자 리스트 받아 조회.
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<UserListResponseDto> searchUsers(UserListRequestDto dto, Pageable pageable) {

        QUser user = QUser.user;
//        BooleanBuilder booleanBuilder = new BooleanBuilder();
//
//        if (dto.getId() != null && !dto.getId().isBlank()) {
//            booleanBuilder.and(user.id.eq(dto.getId()));
//        }
//        if (dto.getName() != null && !dto.getName().isBlank()) {
//            booleanBuilder.and(user.name.eq(dto.getName()));
//        }
//        if (dto.getLevel() != null && !dto.getLevel().isBlank()) {
//            booleanBuilder.and(user.level.eq(dto.getLevel()));
//        }
//        if (dto.getDesc() != null && !dto.getDesc().isBlank()) {
//            booleanBuilder.and(user.desc.contains(dto.getDesc()));
//        }
//        if (dto.getRegDate() != null) {
//            Timestamp startDate = Timestamp.valueOf(dto.getRegDate().atStartOfDay());
//            Timestamp endDate = Timestamp.valueOf(dto.getRegDate().plusDays(1).atStartOfDay());
//            booleanBuilder.and(user.regDate.goe(startDate)); // 이상
//            booleanBuilder.and(user.regDate.lt(endDate)); //  미만
//        }

        List<UserListResponseDto> content = jpaQueryFactory
                .select(new QUserListResponseDto(
                        user.id,
                        user.name,
                        user.level,
                        user.desc,
                        user.regDate
                ))
                .from(user)
                .where(
                        idEq(dto.getId()),
                        nameEq(dto.getName()),
                        levelEq(dto.getLevel()),
                        descContains(dto.getDesc()),
                        regDateEq(dto.getRegDate())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory
                .select(user.count())
                .from(user)
                .where(
                        idEq(dto.getId()),
                        nameEq(dto.getName()),
                        levelEq(dto.getLevel()),
                        descContains(dto.getDesc()),
                        regDateEq(dto.getRegDate())
                )
                .fetchOne();

        long nullCheckTotal = total != null
                ? total
                : 0L;

        return new PageImpl<>(content, pageable, nullCheckTotal);
    }


    /**
     * QueryDsl 사용시 id가 null, 공백 체크하는 메서드
     */    
    private Predicate idEq(String id) {
        if (id != null && !id.isBlank()) {
            return QUser.user.id.eq(id);
        } else {
            return null;
        }
    }

    /**
     * QueryDsl 사용시 name null, 공백 체크하는 메서드
     */    
    private Predicate nameEq(String name) {
        if (name != null && !name.isBlank()) {
            return QUser.user.name.eq(name);
        } else {
            return null;
        }
    }

    /**
     * QueryDsl 사용시 level이 null, 공백 체크하는 메서드
     */    
    private Predicate levelEq(String level) {
        if (level != null && !level.isBlank()) {
            return QUser.user.level.eq(level);
        } else {
            return null;
        }
    }

    /**
     * QueryDsl 사용시 desc가 null,공백 체크하는 확인하는 메서드
     * 이건 contains를 사용하여 해당하는 문자가 포함되어 있는지 확인한다.
     */    
    private Predicate descContains(String desc) {
        if (desc != null && !desc.isBlank()) {
            return QUser.user.desc.contains(desc);
        } else {
            return null;
        }
    }

    /**
     * QueryDsl 사용시 regDate가 null 체크하는 메서드
     */
    private Predicate regDateEq(LocalDate regDate) {
        if (regDate != null) {
            Timestamp startRegDate = Timestamp.valueOf(regDate.atStartOfDay());
            Timestamp endRegDate = Timestamp.valueOf(regDate.plusDays(1).atStartOfDay());
            return QUser.user.regDate.goe(startRegDate).and(QUser.user.regDate.lt(endRegDate));
        } else  {
            return null;
        }
    }
}
