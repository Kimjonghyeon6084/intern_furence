package com.example.assignment.service;

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
     * 로그인 메서드.
     * @param dto
     * @return LoginResDto
     */
    public LoginResDto login(LoginReqDto dto) {
//        return userRepository.findByIdAndPwd(dto.getId(), dto.getPwd())
//                .map(user -> new LoginResDto(dto.getId(), dto.getPwd()))
//                .orElseThrow(() -> new IllegalArgumentException("아이디 혹은 비밀번호가 틀립니다."));
        Optional<User> checkUserId = userRepository.findById(dto.getId());
        if (checkUserId.isEmpty()) {
            throw new IllegalArgumentException("아이디가 틀렸습니다.");
        }
        User user = checkUserId.get();
        if (!user.getPwd().equals(dto.getPwd())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
        return new LoginResDto(user.getId(), user.getPwd());
    }

    /**
     * 유저 리스트 불러오는 메서드(비밀번호만 빼고 모두다)
     * @param page
     * @param size
     * @return
     */
    public Page<UserListDto> findAllExceptPwd(int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // pageable 객체 생성
        return userRepository.findAllExceptPwd(pageable);
    }
}
