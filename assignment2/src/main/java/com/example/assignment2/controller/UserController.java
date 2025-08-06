package com.example.assignment2.controller;

import com.example.assignment2.domain.dto.user.*;
import com.example.assignment2.domain.dto.user.info.*;
import com.example.assignment2.domain.dto.user.list.UserListRequestDto;
import com.example.assignment2.domain.dto.user.list.UserListResponseDto;
import com.example.assignment2.domain.dto.user.login.LoginRequestDto;
import com.example.assignment2.domain.dto.user.login.LoginResponseDto;
import com.example.assignment2.domain.dto.user.login.LoginStatus;
import com.example.assignment2.domain.dto.user.login.SessionUserDto;
import com.example.assignment2.domain.entity.User;
import com.example.assignment2.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 로그인
     * 기존 세션이 있다면 무효화 후 새로운 세션을 만들어서 로그인 시킴.
     * @param dto LoginReqDto dto
     * @param session HTTPSession
     * @param request HttpServletRequest
     * @return ResponseEntity<LoginResDto>
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> signin(@RequestBody @Valid LoginRequestDto dto,
                                                   HttpSession session,
                                                   HttpServletRequest request) {
        LoginResponseDto resDto = userService.login(dto);

        if (resDto.getLoginStatus() == LoginStatus.FAILURE) {
            // 로그인 실패시
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(resDto);
        }
        // 세션에 넣을 dto
        SessionUserDto sessionUserDto = SessionUserDto.builder()
                .id(resDto.getId())
                .name(resDto.getName())
                .build();

        // session 등록 전 기존 세션이 있다면
        // 파기(세션 고정 공격 방지, 이전 사용자 정보/값 초기화, 사용자 전환 이슈 방지)
        SessionUserDto checkSessionUserDto = (SessionUserDto)session.getAttribute("userInfo");
        if (checkSessionUserDto != null && checkSessionUserDto.getId().equals(sessionUserDto.getId())) {
            session.invalidate();
        }

        HttpSession newSession = request.getSession(true);
        newSession.setAttribute("userInfo", sessionUserDto);
        log.info("로그인 성공");
        return ResponseEntity.ok(resDto);
    }

    @GetMapping("/user/list/{page}")
    public ResponseEntity<Page<UserListResponseDto>> selectUsers (@PathVariable int page,
                                                                  @Valid UserListRequestDto dto) {
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<UserListResponseDto> result = userService.selectUsers(dto, pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/user/signup")
    public ResponseEntity<UserSignupResponseDto> signup(@RequestBody @Valid UserSignupRequestDto dto) {

        log.info("dto : {}", dto);
        UserSignupResponseDto result = this.userService.signup(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserEditResponseDto> getUser(@PathVariable String id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(UserEditResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .level(user.getLevel())
                .desc(user.getDesc())
                .build());
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<UserEditResponseDto> editUser(
            @PathVariable("userId") String userId,
            @RequestBody UserEditRequestDto dto) {
        UserEditResponseDto result = userService.editUser(userId, dto);
        return ResponseEntity.ok(result);
    }
}

