package com.example.assignment.common.config;

import com.example.assignment.common.interceptor.LoggingInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) { // 어플 내에 interceptor 등록해주는 메서드
        registry.addInterceptor(new LoggingInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/favicon.ico", "/.well-known/**", "/userlist", "/css/**", "/js/**");
    }
}
