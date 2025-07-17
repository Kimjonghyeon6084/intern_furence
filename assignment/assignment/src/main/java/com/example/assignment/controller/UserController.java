package com.example.assignment.controller;

import com.example.assignment.domain.dto.LoginSuccessField;
import com.example.assignment.domain.dto.user.LoginReqDto;
import com.example.assignment.domain.dto.user.SessionUserDto;
import com.example.assignment.domain.dto.user.UserListDto;
import com.example.assignment.domain.dto.user.LoginResDto;
import com.example.assignment.domain.entity.User;
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

    /**
     * login 창 반환
     * @return
     */
    @GetMapping("/login")
    public String GoLoginHomePage() {
        return "login";
    }

    /**
     * userlist 창 반환
     * @return
     */
    @GetMapping("/userlist")
    public String GoUserListPage() {
        return "userlist";
    }

    /**
     * 페이징된 유저정보 조회
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
     * 로그인
     * 기존 세션이 있다면 무효화 후 새로운 세션을 만들어서 로그인 시킴.
     * @param dto LoginReqDto dto
     * @param session HTTPSession
     * @param request HttpServletRequest
     * @return ResponseEntity<LoginResDto>
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResDto> signin(@RequestBody @Valid LoginReqDto dto,
                                                        HttpSession session,
                                                        HttpServletRequest request) {
        LoginResDto resDto = userService.login(dto);
        if (resDto.getSuccessField() == LoginSuccessField.SUCCESS) {
            // 세션에 넣을 dto
            SessionUserDto sessionUserDto = new SessionUserDto(resDto.getId(), resDto.getName());

            if (resDto != null) {
                // session 등록 전 기존 세션이 있다면
                // 파기(세션 고정 공격 방지, 이전 사용자 정보/값 초기화, 사용자 전환 이슈 방지)
                if (session.getAttribute("userInfo") != null) {
                    session.invalidate();
                }
            }

            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("userInfo", sessionUserDto);
            log.info(newSession.getAttribute("userInfo").toString());
            log.info("로그인 성공");
            return ResponseEntity.ok(resDto);
        }

        // 로그인 실패시
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(resDto);
    }
}
