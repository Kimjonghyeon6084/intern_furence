package com.example.assignment.repository;

import com.example.assignment.domain.dto.user.UserListDto;
import com.example.assignment.domain.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByIdAndPwd(String id, String pwd);

    //pwd 빼고 모든 유저 정보 가져오기.
    @Query("select new com.example.assignment.domain.dto.user.UserListDto(u.id, u.name, u.level, u.desc, u.reg_date) from t_user u")
    Page<UserListDto> findAllExceptPwd(Pageable pageable);

};
