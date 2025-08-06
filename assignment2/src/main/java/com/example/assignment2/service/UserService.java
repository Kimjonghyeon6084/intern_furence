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

//        User user = this.userRepository.findById(dto.getId())
//                .orElse(new User());

//        LoginStatus loginStatus = false == user.getPwd().equals(dto.getPwd())
//                ? LoginStatus.FAILURE : LoginStatus.SUCCESS;
//        LoginValidField field = user.getPwd().equals(dto.getPwd())
//                ? null : user == null ? LoginValidField.ID : LoginValidField.PWD;
//
//        String message = user.getPwd().equals(dto.getPwd())
//                ? null : user.equals("") ? LoginResponseMessage.INVALID_ID.getMessage()
//                : LoginResponseMessage.INVALID_PASSWORD.getMessage();
//
//        String id = user.equals("") ? null : user.getId();
//        String name = user.equals("") ? null : user.getName();
//
//        return LoginResponseDto.builder()
//                .loginStatus(loginStatus)
//                .loginValidField(field)
//                .message(message)
//                .id(id)
//                .name(name)
//                .build();


        Optional<User> checkUserIdOpt = userRepository.findById(dto.getId());


        if (checkUserIdOpt.isEmpty()) { // 아이디가 틀렸을 때
            return LoginResponseDto.builder()
                        .loginStatus(LoginStatus.FAILURE)
                        .loginValidField(LoginValidField.ID)
                        .message(LoginResponseMessage.INVALID_ID.getMessage())
                        .build();
        }

        User user = checkUserIdOpt.get();

        if (!user.getPwd().equals(dto.getPwd())) { // 비밀번호가 틀렸을 때
            return LoginResponseDto.builder()
                    .loginStatus(LoginStatus.FAILURE)
                    .loginValidField(LoginValidField.PWD)
                    .message(LoginResponseMessage.INVALID_PASSWORD.getMessage())
                    .build();
        }

        return LoginResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .loginStatus(LoginStatus.SUCCESS)
                .loginValidField(null)
                .build();
    }

    /**
     * 조건에 맞는 유저 리스트 불러오기
     */
    public Page<UserListResponseDto> selectUsers(UserListRequestDto dto, Pageable pageable) {
        return this.userRepository.searchUsers(dto, pageable);
    }

    public UserSignupResponseDto signup(UserSignupRequestDto dto) {

        log.info("dto"+dto);

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
        return this.userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 회원이 존재하지 않습니다."));
    }

    public UserEditResponseDto editUser(String id, UserEditRequestDto dto) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 회원이 존재하지 않습니다."));

        this.userRepository.save(user);

        return UserEditResponseDto.builder()
//                .id(dto.getId())
                .pwd(dto.getPwd())
                .name(dto.getName())
                .level(dto.getLevel())
                .desc(dto.getDesc())
                .modDate(dto.getModDate())
                .build();


    }
}