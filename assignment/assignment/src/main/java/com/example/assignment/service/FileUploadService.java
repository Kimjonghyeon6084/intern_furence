package com.example.assignment.service;

import com.example.assignment.common.exception.CustomException;
import com.example.assignment.common.exception.ErrorCode;
import com.example.assignment.domain.dto.file.FileUploadRequestDto;
import com.example.assignment.domain.dto.file.UploadErrorResponseDto;
import com.example.assignment.domain.dto.file.UploadResultResponseDto;
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
    private final InsertFileService insertFileService;

//    private static final int INITIAL_SUCCESS_COUNT = 0;
    /**
     * 파일 업로드하는 메서드
     * "/"로 잘라 각 항목을 DTO에 담고
     * FileUploadDto.validate()를 통해서 각 항목을 검사한다.
     * 성공건수는 success에 담아주고 오류는 errors에 담아 프론트로 넘겨줌
     **/
    public UploadResultResponseDto processFile(MultipartFile file) {
        int success = 0;
        List<UploadErrorResponseDto> errors = new ArrayList<>();
        List<String> lines;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            lines = reader.lines().toList();
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                String[] parts = line.split("/");
                if (parts.length != 6) {
                    errors.add(new UploadErrorResponseDto(i + 1, line));
                    continue;
                }
                FileUploadRequestDto dto = FileUploadRequestDto.fromParts(parts);
                try {
                    dto.validate(i);
                    insertFileService.insertFile(dto.toEntity());
                    success++;
                } catch (PersistenceException e) {
                    errors.add(new UploadErrorResponseDto(i + 1, line));
                    log.warn("✅파일 업로드 실패 (라인 {}): {} / 원인: {}", i + 1, line, ErrorCode.DB_DUPLICATE.getMessage());
                } catch (CustomException e) {
                    errors.add(new UploadErrorResponseDto(i + 1, line));
                    log.warn("✅파일 업로드 실패 (라인 {}): {} / 원인: {}", i + 1, line, e.getErrorCode().getMessage());
                }
            }
        } catch (IOException e) {
            errors.add(new UploadErrorResponseDto(0, "파일 읽기 실패: " + e.getMessage()));
            throw new CustomException(ErrorCode.UNKNOWN_ERROR, "파일 읽기 실패: " + e.getMessage());
        }
        return new UploadResultResponseDto(success, errors);
    }

    /**
     * 처음 파일 업로드하고 나서 페이징처리 전 가볍게 보여주는 메서드.
     * @return
     */
    public List<User> findAll() {
        log.info("✅DB정보 불러오기");
        return userRepository.findAll();
    }
}