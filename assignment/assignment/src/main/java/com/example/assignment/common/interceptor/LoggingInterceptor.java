package com.example.assignment.common.interceptor;

import com.example.assignment.domain.dto.user.SessionUserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 요청을 가로채는 메서드
 * 메서드 요청전, 요청후, 렌더링 이후 처리 가능.
 */
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {
    /**
     * 메서드 요청 전 메서드
     * 로그인이 필요한 url에 접근시 세션 검사후
     * 세션이 유효하면 그대로 진행, 유효하지 않으면 /login 으로 redirect.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        log.info("요청 URL : {}", request.getRequestURI());
        log.info("preHandle() 동작");
        SessionUserDto value = (SessionUserDto) request.getSession().getAttribute("userInfo");
        if (value == null) {
            response.sendRedirect("/login");
            return false;
        } else {
            return true;
        }
    }
}
