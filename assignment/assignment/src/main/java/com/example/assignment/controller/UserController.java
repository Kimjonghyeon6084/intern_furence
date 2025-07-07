package com.example.assignment.controller;

import com.example.assignment.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

//    private final UserService userService;

    @GetMapping("/login")
    public String tologinPage(){
        return "login";
    }

//    public

}
