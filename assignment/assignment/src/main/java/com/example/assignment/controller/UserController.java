package com.example.assignment.controller;

import com.example.assignment.domain.dto.user.LoginErrorResDto;
import com.example.assignment.domain.dto.user.LoginReqDto;
import com.example.assignment.domain.dto.user.UserListDto;
import com.example.assignment.domain.dto.user.LoginResDto;
import com.example.assignment.service.UserService;

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

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String tologinPage() {
        return "login";
    }

    @GetMapping("/userlist")
    public String toUserListpage() {
        return "userlist";
    }

    @PostMapping("/login")
    public ResponseEntity<?> signin(@RequestBody @Valid LoginReqDto dto, HttpSession session) {
        LoginResDto resDto = userService.login(dto);
        if (resDto != null) {
            log.info("로그인 성공");
            session.setAttribute("loginUser", dto.getId());
            return ResponseEntity.ok(resDto);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginErrorResDto("아이디 혹은 비밀번호가 틀립니다."));
    }

    @GetMapping("/user/list")
    public ResponseEntity<List<UserListDto>> uploadUser(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Page<UserListDto> list = userService.findAllExceptPwd(page, size);
        return ResponseEntity.ok(list.getContent());
    }

}
