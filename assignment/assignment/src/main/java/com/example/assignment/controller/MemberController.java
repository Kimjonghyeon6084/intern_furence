package com.example.assignment.controller;

import com.example.assignment.dto.UploadResult;
import com.example.assignment.entity.User;
import com.example.assignment.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/upload")
    public String uploadPage(){
        return "upload";
    }

    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file")MultipartFile file, Model model){
        if(!file.getOriginalFilename().endsWith(".dbfile")){
            model.addAttribute(("error"), ".dbfile 확장자만 업로드 가능합니다.");
            return "upload";
        }
        UploadResult result = memberService.processFile(file);
        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/data")
    @ResponseBody
    public List<User> readData() {
        return memberService.findAll();
    }
}
