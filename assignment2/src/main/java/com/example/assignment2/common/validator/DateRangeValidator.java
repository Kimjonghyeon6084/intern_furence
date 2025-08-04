package com.example.assignment2.common.validator;

import com.example.assignment2.common.customAnnotation.CheckDateRangeValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

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

            if (start == null || end == null) return true;

            // LocalDate, LocalDateTime, 등 Temporal 인터페이스 비교
            if (start instanceof LocalDate && end instanceof LocalDate) {
                return !((LocalDate ) start).isAfter((LocalDate ) end);
            }
        } catch (Exception e) {
            // 필드가 없거나 타입 불일치 등은 통과(혹은 false로)
            return true;
        }
        return true;
    }
}
