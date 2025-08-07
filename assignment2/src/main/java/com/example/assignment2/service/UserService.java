package com.example.assignment2.service;

import com.example.assignment2.domain.dto.user.info.UserEditRequestDto;
import com.example.assignment2.domain.dto.user.info.UserEditResponseDto;
import com.example.assignment2.domain.dto.user.info.UserSignupRequestDto;
import com.example.assignment2.domain.dto.user.info.UserSignupResponseDto;
import com.example.assignment2.domain.dto.user.list.UserListRequestDto;
import com.example.assignment2.domain.dto.user.list.UserListResponseDto;
import com.example.assignment2.domain.dto.user.login.*;
import com.example.assignment2.domain.entity.User;
import com.example.assignment2.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    /**
     * 로그인 메서드. 로그인시 아이디가 틀렸는지, 비밀번호가 틀렸는지 검증함
     * @param dto
     * @return
     */
    public LoginResponseDto login(LoginRequestDto dto) {

        return this.userRepository.findById(dto.getId())
            .map(user -> dto.getPwd().equals(user.getPwd())
                    ? buildSuccessResponse(user)
                    : buildFailResponse(LoginFailType.PWD))
            .orElse(buildFailResponse(LoginFailType.ID));
    }

    /**
     * 조건에 맞는 유저 리스트 불러오기
     */
    public Page<UserListResponseDto> selectUsers(UserListRequestDto dto, Pageable pageable) {
        return this.userRepository.searchUsers(dto, pageable);
    }

    public UserSignupResponseDto signup(UserSignupRequestDto dto) {

        if(this.userRepository.existsById(dto.getId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        User user = User.builder()
                .id(dto.getId())
                .pwd(dto.getPwd())
                .level(dto.getLevel())
                .name(dto.getName())
                .regDate(LocalDateTime.now())
                .build();

        this.userRepository.save(user);

        return new UserSignupResponseDto(user.getId(), user.getName());
    }

    public User findById(String id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 회원이 존재하지 않습니다."));
    }

    public UserEditResponseDto editUser(String id, UserEditRequestDto dto) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 회원이 존재하지 않습니다."));

        user.updateFrom(dto);
        this.userRepository.save(user);

        return UserEditResponseDto.builder()
                .id(id)
                .name(dto.getName())
                .build();
    }

    /**
     * 로그인 성공했을 때 메서드
     */
    private LoginResponseDto buildSuccessResponse(User user) {
        return LoginResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .loginStatus(LoginStatus.SUCCESS)
                .build();
    }

    /**
     * 로그인 실패시 메서드
     */
    private LoginResponseDto buildFailResponse(LoginFailType loginFailType) {
        return LoginResponseDto.builder()
                .loginStatus(LoginStatus.FAILURE)
                .loginFailType(loginFailType.getField())
                .message(loginFailType.getMessage())
                .build();
    }
}