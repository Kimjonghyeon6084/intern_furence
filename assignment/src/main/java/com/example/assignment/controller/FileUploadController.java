package com.example.assignment.controller;

import com.example.assignment.domain.dto.file.UploadError;
import com.example.assignment.domain.dto.file.UploadResult;
import com.example.assignment.domain.entity.User;
import com.example.assignment.service.FileUploadService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 파일 업로드 controller
 */
@Controller
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    /**
     * 파일 업로드 html 반환
     * @return
     */
    @GetMapping("/upload")
    public String toUploadPage() {
        return "upload";
    }

    /**
     * 파일 업로드 처리
     * .dbfile 확장자만 가능하게 함.
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ResponseEntity<UploadResult> fileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.getOriginalFilename().endsWith(".dbfile")) {
            List<UploadError> errors = new ArrayList<>();
            errors.add(new UploadError(0, ".dbfile 확장자만 업로드 가능합니다."));
            return ResponseEntity.ok(new UploadResult(0, errors));
        }
        UploadResult result = fileUploadService.processFile(file);
        return ResponseEntity.ok(result);
    }

    /**
     * 저장된 모든 유저정보 조회
     * @return
     */
    @GetMapping("/data")
    public ResponseEntity<List<User>> findAllUser(){
        return ResponseEntity.ok(fileUploadService.findAll());
    }
}
