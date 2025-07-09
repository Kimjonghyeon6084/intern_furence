package com.example.assignment.controller;

import com.example.assignment.domain.dto.user.LoginErrorResDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

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

    @GetMapping("/user/list")
    public ResponseEntity<List<UserListDto>> showUserList(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Page<UserListDto> list = userService.findAllExceptPwd(page, size);
        log.info("유저정보 불러오기 성공");
        return ResponseEntity.ok(list.getContent());
    }

    @PostMapping("/login")
    public ResponseEntity<?> signin(@RequestBody @Valid LoginReqDto dto, HttpSession session, HttpServletRequest request) {
        LoginResDto resDto = userService.login(dto);
        if (resDto != null) {
            // session 등록 전 기존 세션이 있다면
            // 파기(세션 고정 공격 방지, 이전 사용자 정보/값 초기화, 사용자 전환 이슈 방지)
            if (session.getId() != null) {
                session.invalidate();
            }
            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("sessionID", dto.getId());
            log.info("로그인 성공");
            return ResponseEntity.ok(resDto);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginErrorResDto("아이디 혹은 비밀번호가 틀립니다."));
    }


}
