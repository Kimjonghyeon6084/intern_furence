package com.example.assignment.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * login html창 업로드 메서드
     * @return
     */
    @GetMapping("/login")
    public String GoLoginHomePage() {
        return "login";
    }

    /**
     * userlist html창 업로드 메서드
     * @return
     */
    @GetMapping("/userlist")
    public String GoUserListPage() {
        return "userlist";
    }

    /**
     * 페이징된 유저정보를 보여주는 메서드
     * @param page
     * @return ResponseEntity<?>
     */
    @GetMapping("/user/list/{page}")
    public ResponseEntity<?> showUserList(@PathVariable int page) {
        int size = 10;
        // 미리 로그인한 유저인지 확인
//        if (value == null) {
//            return ExceptionResponse.fail(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다..");
//        } else {
            Page<UserListDto> list = userService.findAllExceptPwd(page, size);
            log.info("유저정보 불러오기 성공");
            return ResponseEntity.ok(list);
//        }
    }

    /**
     * 로그인 메서드.
     * 로그인을 했던 유저가 세션 만료 기간 전 로그인을 하면 파기를 하고 새로운 세션을 만들어서 로그인 시킴.
     * @param LoginReqDto dto
     * @param HTTPSession session
     * @param HttpServletRequest request
     * @return ResponseEntity<LoginResDto>
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResDto> signin(@RequestBody @Valid LoginReqDto dto,
                                                        HttpSession session,
                                                        HttpServletRequest request) {
        LoginResDto resDto = userService.login(dto);
        if (resDto != null) {
            // session 등록 전 기존 세션이 있다면
            // 파기(세션 고정 공격 방지, 이전 사용자 정보/값 초기화, 사용자 전환 이슈 방지)
            if (session.getAttribute("sessionID") != null) {
                log.info((String) session.getAttribute("sessionID"));
                session.invalidate();
            }
        }

        HttpSession newSession = request.getSession(true);
        newSession.setAttribute("sessionID", dto.getId());
        log.info((String) newSession.getAttribute("sessionID"));
        log.info("로그인 성공");
        return ResponseEntity.ok(resDto);
    }
}
