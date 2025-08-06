package com.example.assignment2.controller;

import com.example.assignment2.domain.entity.User;
import com.example.assignment2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class PageController {

    private final UserService userService;

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

    @GetMapping("/edit/userinfo")
    public String goEditUserinfoPage(@RequestParam String id, Model model) {
        User user = this.userService.findById(id);
        model.addAttribute("user", user);
        return "edituserinfo";
    }

    @GetMapping("/signup")
    public String goSignupPage() {
        return "signup";
    }
}
