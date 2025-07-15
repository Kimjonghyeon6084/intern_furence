package com.example.assignment.common.interceptor;

import com.example.assignment.common.exception.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 요청을 가로채는 메서드
 * 메서드 요청전, 요청후, 렌더링 이후 처리 가능.
 */
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {
    /**
     * 메서드 요청 전 메서드
     * 로그인이 필요한 url에 로그인 없이 접근시
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        log.info("요청 URL : {}", request.getRequestURI());
        log.info("preHandle() 동작");
        String value = (String)request.getSession().getAttribute("sessionID");
        if (value == null) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            String json = String.valueOf(ExceptionResponse.fail(HttpStatus.UNAUTHORIZED, "로그인이 필요한 페이지입니다."));
//            response.getWriter().write(json);
            response.sendRedirect("/login");
            return false;
        } else {
            return true;
        }
    }
}
