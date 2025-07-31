package com.example.assignment2.repository;

import com.example.assignment2.domain.dto.user.UserListRequestDto;
import com.example.assignment2.domain.dto.user.UserListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepositoryCustom {

    Page<UserListResponseDto> searchUsers(UserListRequestDto dto, Pageable pageable);
    List<UserListResponseDto> searchUserList();


}
