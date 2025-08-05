package com.example.assignment2.common.customAnnotation;

import com.example.assignment2.common.validator.DateRangeValidator;
import com.example.assignment2.domain.dto.user.DateValidationMessage;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateRangeValidator.class) // Search 해보자
@Target(ElementType.TYPE) // 어디에 쓰일지
@Retention(RetentionPolicy.RUNTIME) // 생명주기?
public @interface CheckDateRangeValid {

    String startField();

    String endField();

    DateValidationMessage message() default DateValidationMessage.INVALID_RANGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
