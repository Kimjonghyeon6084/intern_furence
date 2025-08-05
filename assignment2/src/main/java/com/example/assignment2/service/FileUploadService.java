package com.example.assignment2.service;

import com.example.assignment2.common.exception.CustomException;
import com.example.assignment2.common.exception.ErrorCode;
import com.example.assignment2.domain.dto.file.FileUploadDto;
import com.example.assignment2.domain.dto.file.UploadError;
import com.example.assignment2.domain.dto.file.UploadResult;
import com.example.assignment2.domain.dto.user.list.UserListResponseDto;
import com.example.assignment2.domain.entity.User;
import com.example.assignment2.repository.UserRepository;
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

    private static final int INITIAL_SUCCESS_COUNT = 0;
    /**
     * 파일 업로드하는 메서드
     * "/"로 잘라 각 항목을 DTO에 담고
     * FileUploadDto.validate()를 통해서 각 항목을 검사한다.
     * 성공건수는 success에 담아주고 오류는 errors에 담아 프론트로 넘겨줌
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
                    insertFileService.insertFile(dto.toEntity());
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

    /**
     * 처음 파일 업로드하고 나서 페이징처리 전 가볍게 보여주는 메서드.
     * @return
     */
//    public List<User> findAll() {
//        log.info("✅DB정보 불러오기");
//        return userRepository.findAll();
//    }

    public List<UserListResponseDto> findUsers() {
        return this.userRepository.searchUserList();
    }
}