package com.example.assignment.repository;

import com.example.assignment.domain.dto.user.UserListDto;
import com.example.assignment.domain.dto.user.UserListRequestDto;
import com.example.assignment.domain.dto.user.UserListResponseDto;
import com.example.assignment.domain.entity.QUser;
import com.example.assignment.domain.entity.User;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA를 이용하기 위한 Repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>, UserRepositoryCustom {

    // 아이디와 비밀번호가 맞는지 검증
    Optional<User> findByIdAndPwd(String id, String pwd);

    //아이디만 맞는지 검증
    Optional<User> findById(String id);

    //pwd 빼고 모든 유저 정보 가져오기.
    @Query("select new com.example.assignment.domain.dto.user.UserListDto(u.id, u.name, u.level, u.desc, u.regDate) from t_user u")
    Page<UserListDto> findAllExceptPwd(Pageable pageable);

};
