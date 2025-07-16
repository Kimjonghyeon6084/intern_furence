package com.example.assignment.service;

import com.example.assignment.common.exception.LoginFailedException;
import com.example.assignment.domain.dto.user.LoginReqDto;
import com.example.assignment.domain.dto.user.UserListDto;
import com.example.assignment.domain.dto.user.LoginResDto;
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
     * 로그인 메서드. 로그인시 아이디가 틀렸는지, 비밀번호가 틀렸는지, 둘 다 틀렸는지 검증함
     * @param LoginReqdto
     * @return LoginResDto
     */
    public LoginResDto login(LoginReqDto dto) {
//        return userRepository.findByIdAndPwd(dto.getId(), dto.getPwd())
//                .map(user -> new LoginResDto(dto.getId(), dto.getPwd()))
//                .orElseThrow(() -> new IllegalArgumentException("아이디 혹은 비밀번호가 틀립니다."));
        Optional<User> checkUserIdOpt = userRepository.findById(dto.getId());
        Optional<User> findByIdAndPwd = userRepository.findByIdAndPwd(dto.getId(), dto.getPwd());

        if (findByIdAndPwd.isEmpty()) { // 아이디, 비밀번호가 맞는지 검증
            if (checkUserIdOpt.isPresent()) { // 아이디가 맞는지 검증
                User user = checkUserIdOpt.get();
                if (!user.getPwd().equals(dto.getPwd())) { // 비밀번호가 맞는지 검증
                    throw new LoginFailedException("비밀번호가 틀렸습니다.");
                } else {
                    throw new LoginFailedException("아이디가 틀렸습니다.");
                }
            } else {
                throw new LoginFailedException("아이디와 비밀번호 모두 틀렸습니다.");
            }
        }
        return LoginResDto.builder()
                .id(dto.getId())
                .pwd(dto.getPwd())
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
}
