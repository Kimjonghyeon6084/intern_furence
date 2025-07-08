package com.example.assignment.service;

import com.example.assignment.domain.dto.user.LoginReqDto;
import com.example.assignment.domain.dto.user.UserListDto;
import com.example.assignment.domain.dto.user.UserResDto;
import com.example.assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResDto login(LoginReqDto dto) {
        return userRepository.findByIdAndPwd(dto.getId(), dto.getPwd())
                .map(user -> new UserResDto(dto.getId(), dto.getPwd()))
                .orElse(null);
    }

    public Page<UserListDto> findAllExceptPwd(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAllExceptPwd(pageable);
    }
}
