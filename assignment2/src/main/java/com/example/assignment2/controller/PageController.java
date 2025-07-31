package com.example.assignment2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class PageController {

    @GetMapping("/login")
    public String goLoginPage() {
        return "login";
    }

    @GetMapping("/userlist")
    public String goUserlistPage() {
        return "userlist";
    }

    @GetMapping("/fileupload")
    public String goFileUploadPage() {
        return "fileupload";
    }
}
