package com.example.assignment.common.aop;

import com.example.assignment.common.customAnnotation.CheckFileExtension;
import com.example.assignment.common.exception.CustomException;
import com.example.assignment.common.exception.ErrorCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @CheckFileExtension 이 붙은 메서드가 실행되면 AOP가 가로채서 MultipartFile 파라미터의 파일 확장자를 검사,
 * 조건을 만족하지 않으면 CustomException throw
 */
@Aspect
@Component
public class FileExtensionAspect {

    @Around("@annotation(checkFileExtension)")
    public Object validateFileExtension(ProceedingJoinPoint joinPoint, CheckFileExtension checkFileExtension) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String[] allowedExtensions = checkFileExtension.value();

        for (Object arg : args) {
            if (arg instanceof MultipartFile file) {
                String fileName = file.getOriginalFilename();
                boolean valid = false;
                if (fileName != null) {
                    for (String ext : allowedExtensions) {
                        if (fileName.toLowerCase().endsWith(ext.toLowerCase())) {
                            valid = true;
                            break;
                        }
                    }
                }
                if (!valid) {
                    throw new CustomException(ErrorCode.INVALID_FILE_EXTENSION, ErrorCode.INVALID_FILE_EXTENSION.getMessage());
                }
            }
        }
        return joinPoint.proceed();
    }
}