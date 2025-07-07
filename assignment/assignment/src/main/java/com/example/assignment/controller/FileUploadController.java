package com.example.assignment.controller;

import com.example.assignment.domain.dto.UploadError;
import com.example.assignment.domain.dto.UploadResult;
import com.example.assignment.domain.entity.User;
import com.example.assignment.service.FileUploadService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    @ResponseBody
    public ResponseEntity<UploadResult> fileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.getOriginalFilename().endsWith(".dbfile")) {
            List<UploadError> errors = new ArrayList<>();
            errors.add(new UploadError(0, ".dbfile 확장자만 업로드 가능합니다."));
            return ResponseEntity.badRequest().body(new UploadResult(0, errors));
        }
        UploadResult result = fileUploadService.processFile(file);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/data")
    @ResponseBody
    public ResponseEntity<List<User>> findAllUser(){
        return ResponseEntity.ok(fileUploadService.findAll());
    }
}
