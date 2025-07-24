package com.example.assignment.common.customAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 파일 확장자 체크하기 위한 커스텀 어노테이션
 * @Target : 사용할 곳
 * @Retention : 어노테이션 정보가 언제까지 남아 있을지 정하는 것
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckFileExtension {

    String value();

}
