package com.example.assignment.service;

import com.example.assignment.common.exception.CustomException;
import com.example.assignment.common.exception.ErrorCode;
import com.example.assignment.domain.dto.file.FileUploadDto;
import com.example.assignment.domain.dto.file.UploadError;
import com.example.assignment.domain.dto.file.UploadResult;
import com.example.assignment.domain.entity.User;
import com.example.assignment.repository.UserRepository;

import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileUploadService {

    private final UserRepository userRepository;
    private final InsertOneService insertOneService;

    private static final int INITIAL_SUCCESS_COUNT = 0;
    /**
     * 파일 업로드하는 메서드
     **/
    public UploadResult processFile(MultipartFile file) {
        int success = 0;
        List<UploadError> errors = new ArrayList<>();
        List<String> lines;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            lines = reader.lines().toList();
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                String[] parts = line.split("/");
                if (parts.length != 6) {
                    errors.add(new UploadError(i + 1, line + " (필드 개수 오류)"));
                    continue;
                }
                FileUploadDto dto = FileUploadDto.fromParts(parts);
                try {
                    dto.validate(i);
                    insertOneService.insertFileOne(dto.toEntity());
                    success++;
                } catch (PersistenceException e) {
                    errors.add(new UploadError(i + 1, line + " (중복된 id)"));
                    log.warn("✅파일 업로드 실패 (라인 {}): {} / 원인: {}", i + 1, line, ErrorCode.DB_DUPLICATE.getMessage());
                } catch (CustomException e) {
                    errors.add(new UploadError(i + 1, line));
                    log.warn("✅파일 업로드 실패 (라인 {}): {} / 원인: {}", i + 1, line, e.getErrorCode().getMessage());
                }
            }
        } catch (IOException e) {
            errors.add(new UploadError(0, "파일 읽기 실패: " + e.getMessage()));
            throw new CustomException(ErrorCode.UNKNOWN_ERROR, "파일 읽기 실패: " + e.getMessage());
        }
        return new UploadResult(success, errors);
    }

    public List<User> findAll() {
        log.info("✅DB정보 불러오기");
        return userRepository.findAll();
    }
}