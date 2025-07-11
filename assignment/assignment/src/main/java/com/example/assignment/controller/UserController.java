package com.example.assignment.controller;

import com.example.assignment.common.exception.ExceptionResponse;
import com.example.assignment.domain.dto.user.LoginReqDto;
import com.example.assignment.domain.dto.user.UserListDto;
import com.example.assignment.domain.dto.user.LoginResDto;
import com.example.assignment.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String GoLoginHomePage() {
        return "login";
    }

    @GetMapping("/userlist")
    public String GoUserListPage() {
        return "userlist";
    }

    @GetMapping("/user/list/{page}")
    public ResponseEntity<?> showUserList(@PathVariable int page,
                                          @SessionAttribute(name = "sessionID", required = false) String value) {
        int size = 10;
        // 미리 로그인한 유저인지 확인
        if (value == null) {
            return ExceptionResponse.fail(HttpStatus.UNAUTHORIZED, "LOGIN001", "로그인이 필요합니다..");
        } else {
            Page<UserListDto> list = userService.findAllExceptPwd(page, size);
            log.info("유저정보 불러오기 성공");
            return ResponseEntity.ok(list);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> signin(@RequestBody @Valid LoginReqDto dto,
                                                        HttpSession session,
                                                        HttpServletRequest request) {
        LoginResDto resDto = userService.login(dto);
        if (resDto != null) {
            // session 등록 전 기존 세션이 있다면
            // 파기(세션 고정 공격 방지, 이전 사용자 정보/값 초기화, 사용자 전환 이슈 방지)
            if (session.getId() != null) {
                session.invalidate();
            }
        }
        HttpSession newSession = request.getSession(true);
        newSession.setAttribute("sessionID", dto.getId());
        log.info("로그인 성공");
        return ResponseEntity.ok(resDto);

    }


}
