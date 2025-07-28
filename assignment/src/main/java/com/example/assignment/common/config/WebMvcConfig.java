package com.example.assignment.common.config;

import com.example.assignment.common.interceptor.LoginInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfigurer을 구현해서 웹 애플리케이션 MVC 구성을 설정하는 곳.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * interceptor로 체크할 url과 체크하지 않을 url 처리
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) { // 어플 내에 interceptor 등록해주는 메서드
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/json","/login", "/favicon.ico", "/.well-known/**", "/css/**", "/js/**");
    }
}
