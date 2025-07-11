package com.example.assignment.service;

import com.example.assignment.domain.dto.user.LoginReqDto;
import com.example.assignment.domain.dto.user.UserListDto;
import com.example.assignment.domain.dto.user.LoginResDto;
import com.example.assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public LoginResDto login(LoginReqDto dto) {
        return userRepository.findByIdAndPwd(dto.getId(), dto.getPwd())
                .map(user -> new LoginResDto(dto.getId(), dto.getPwd()))
                .orElseThrow(() -> new IllegalArgumentException("아이디 혹은 비밀번호가 틀립니다."));
    }

    public Page<UserListDto> findAllExceptPwd(int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // pageable 객체 생성
        return userRepository.findAllExceptPwd(pageable);
    }
}
