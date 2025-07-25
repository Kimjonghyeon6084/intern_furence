package com.example.assignment.controller;

import com.example.assignment.common.customAnnotation.CheckFileExtension;
import com.example.assignment.domain.dto.file.UploadErrorResponseDto;
import com.example.assignment.domain.dto.file.UploadResultResponseDto;
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
    @CheckFileExtension({".dbfile", ".txt"})
    public ResponseEntity<UploadResultResponseDto> fileUpload(@RequestParam("file") MultipartFile file) {
        UploadResultResponseDto result = fileUploadService.processFile(file);
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
