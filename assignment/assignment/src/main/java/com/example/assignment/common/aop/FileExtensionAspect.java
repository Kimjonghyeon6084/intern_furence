package com.example.assignment.common.aop;

import com.example.assignment.common.customAnnotation.CheckFileExtension;
import com.example.assignment.common.exception.CustomException;
import com.example.assignment.common.exception.ErrorCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * @CheckFileExtension 이 붙은 메서드가 실행되면 AOP가 가로채서 MultiparFile 파라미터의 파일 확장자를 검사,
 * 조건을 만족하지 않으면 CustomException throw
 * 조건을 만족하면 원래 메서드를 실행한다.(joinPoint.proceed())
 * 에러는 globalExceptionHandler로 처리
 */
@Aspect
@Component
public class FileExtensionAspect {

    @Around("@annotation(checkFileExtension)")
    public Object validateFileExtension(ProceedingJoinPoint joinPoint,
                                        CheckFileExtension checkFileExtension) throws Throwable {
        // 사용하려는 메서드의 매개변수들을 담는다.
        Object[] args = joinPoint.getArgs();
        String[] allowExtension = checkFileExtension.value();
        for (Object arg : args) {
            if (arg instanceof MultipartFile file) {
                boolean isvalid = Arrays
                        .stream(allowExtension)
                        .anyMatch(a -> file.getOriginalFilename().toLowerCase().endsWith(a.toLowerCase()));

                if (!isvalid) {
                    throw new CustomException(ErrorCode.INVALID_FILE_EXTENSION, ErrorCode.INVALID_FILE_EXTENSION.getMessage());
                }
            }
        }
        return joinPoint.proceed();
    }
}