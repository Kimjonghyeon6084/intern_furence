package com.example.assignment2.controller;

import com.example.assignment2.common.customAnnotation.CheckFileExtension;
import com.example.assignment2.domain.dto.file.UploadResult;
import com.example.assignment2.domain.dto.user.UserListResponseDto;
import com.example.assignment2.domain.entity.User;
import com.example.assignment2.service.FileUploadService;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 파일 업로드 controller
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class FileUploadController {

    private final FileUploadService fileUploadService;

    /**
     * 파일 업로드 처리
     * .dbfile 확장자만 가능하게 함.
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @CheckFileExtension({".dbfile"})
    public ResponseEntity<UploadResult> fileUpload(@RequestParam("file") MultipartFile file) {
        UploadResult result = fileUploadService.processFile(file);
        return ResponseEntity.ok(result);
    }

    /**
     * 저장된 모든 유저정보 조회
     * @return
     */
//    @GetMapping("/data")
//    public ResponseEntity<List<User>> findAllUser(){
//        return ResponseEntity.ok(fileUploadService.findAll());
//    }
    @GetMapping("/api/userlist/data")
    public ResponseEntity<List<UserListResponseDto>> findUsers() {
        List<UserListResponseDto> result = fileUploadService.findUsers();
        log.info("result" + result);
        return ResponseEntity.ok(result);
    }
}
