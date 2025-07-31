package com.example.assignment2.common.exception;

/**
 * 로그인 customexception
 */
public class LoginFailedException extends RuntimeException {

    public LoginFailedException(String message) {
        super(message);
    }
}
