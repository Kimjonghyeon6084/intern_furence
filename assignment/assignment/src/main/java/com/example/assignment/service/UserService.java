package com.example.assignment.service;

import com.example.assignment.domain.dto.user.*;
import com.example.assignment.domain.entity.User;
import com.example.assignment.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 로그인 메서드. 로그인시 아이디가 틀렸는지, 비밀번호가 틀렸는지 검증함
     * @param LoginRequestDto
     * @return LoginResDto
     */
    public LoginResponseDto login(LoginRequestDto dto) {

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
     * 유저 리스트 불러오는 메서드(비밀번호만 빼고 모두다)
     * @param page
     * @param size
     * @return Page<UserListDto>
     */
    public Page<UserListDto> findAllExceptPwd(int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // pageable 객체 생성
        return userRepository.findAllExceptPwd(pageable);
    }

    /**
     * 조건에 맞는 유저 리스트 불러오기
     */
    public Page<UserListResponseDto> selectUsers(UserListRequestDto dto, Pageable pageable) {
        return userRepository.searchUsers(dto, pageable);
    }
}
