package com.example.assignment.common.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public String handleCustom(CustomException ex, Model model){
        model.addAttribute(ex.getErrorCode());
        return "error";
    }
}
