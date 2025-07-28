package com.example.assignment.repository;

import com.example.assignment.domain.dto.user.UserListRequestDto;
import com.example.assignment.domain.dto.user.UserListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

    Page<UserListResponseDto> searchUsers(UserListRequestDto dto, Pageable pageable);

}
