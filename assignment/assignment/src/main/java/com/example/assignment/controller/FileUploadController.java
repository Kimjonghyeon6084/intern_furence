package com.example.assignment.controller;

import com.example.assignment.common.exception.CustomException;
import com.example.assignment.common.exception.ErrorCode;
import com.example.assignment.domain.dto.UploadResult;
import com.example.assignment.domain.entity.User;
import com.example.assignment.service.FileUploadService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@AllArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @GetMapping("/upload")
    public String uploadPage(){
        return "upload";
    }

    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file")MultipartFile file, Model model){
        if(!file.getOriginalFilename().endsWith(".dbfile")){
//            model.addAttribute("error", ".dbfile 확장자만 업로드 가능합니다.");
            throw new CustomException(ErrorCode.INVALID_FILE_EXTENSION);
        }
        UploadResult result = fileUploadService.processFile(file);
        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/data")
    @ResponseBody
    public List<User> readData() {
        return fileUploadService.findAll();
    }
}
