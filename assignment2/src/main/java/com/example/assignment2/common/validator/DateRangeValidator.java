package com.example.assignment2.common.validator;

import com.example.assignment2.common.customAnnotation.CheckDateRangeValid;
import com.example.assignment2.domain.dto.user.DateValidationMessage;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.time.LocalDate;

@Slf4j
public class DateRangeValidator implements ConstraintValidator<CheckDateRangeValid, Object> {

    private String startFieldName;

    private String endFieldName;

    @Override
    public void initialize(CheckDateRangeValid constraintAnnotation) {
        this.startFieldName = constraintAnnotation.startField();
        this.endFieldName = constraintAnnotation.endField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            if (value == null) return true;
            Class<?> clazz = value.getClass();
            Field startField = clazz.getDeclaredField(startFieldName);
            Field endField = clazz.getDeclaredField(endFieldName);
            startField.setAccessible(true);
            endField.setAccessible(true);
            Object start = startField.get(value);
            Object end = endField.get(value);

            if (start == null || end == null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(String.valueOf(DateValidationMessage.EMPTY_RANGE))
                        .addConstraintViolation();
                return false;
            }

            // LocalDate 로 들어온 두 객체 비교
            if (start instanceof LocalDate && end instanceof LocalDate) {
                return !((LocalDate)start).isAfter((LocalDate)end);
            }
        } catch (Exception e) {
            log.warn("DateRangeValidator 필드 접근 오류: {}", e.getMessage());
            return false;
        }
        return true;
    }
}
