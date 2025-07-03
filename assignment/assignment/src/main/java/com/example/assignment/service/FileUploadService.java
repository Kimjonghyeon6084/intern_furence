package com.example.assignment.service;

import com.example.assignment.common.exception.CustomException;
import com.example.assignment.common.exception.ErrorCode;
import com.example.assignment.domain.dto.FileUploadDto;
import com.example.assignment.domain.dto.UploadError;
import com.example.assignment.domain.dto.UploadResult;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileUploadService {

    private final UserRepository userRepository;
    private final InsertOneService insertOneService;

//    private final UploadError uploadError;
//    private final UploadResult uploadResult;

    private static final int INITIAL_SUCCESS_COUNT = 0;

    // pk값이 중복되는 것은 못 들어가게 하기 위해 dbfile의 각각의 레코드에 트랜잭션을 걸어 확인 후 insert
    public UploadResult processFile(MultipartFile file) {

        int success = INITIAL_SUCCESS_COUNT;
        List<UploadError> errors = new ArrayList<>();

        log.info("파일 업로드 시작 : {}", file.getOriginalFilename());

        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            List<String> lines = reader.lines().toList();

            for (int i = 0; i < lines.size(); i++){
                String line = lines.get(i).trim();
                String[] parts = line.split("/");

                if(parts.length != 6){
                    errors.add(new UploadError(i + 1, line));
                    throw new CustomException(ErrorCode.VALIDATION_FAIL, "필드 개수 오류, 라인: " + (i + 1));
//                    continue;
                }

                FileUploadDto dto = FileUploadDto.fromParts(parts);

                if(!dto.isValid()){
//                    errors.add(new UploadError(i + 1, line));
//                    continue;
                    throw new CustomException(ErrorCode.VALIDATION_FAIL, "값 유효성 오류, 라인: " + (i + 1));

                }

                try {
                    insertOneService.insertFileOne(dto.toEntity());
                    success++;
                } catch (PersistenceException e) {
                    log.error("DB 저장 실패: userId = {}, 에러 = {}", dto.getId(), e.getMessage());
//                    errors.add(new UploadError(i + 1, line));
                    throw new CustomException(ErrorCode.DB_DUPLICATE, e.getMessage());
                } catch (Exception e) {
                    log.error("처리 중 예외 발생: line {}, 에러: {}", i + 1, e.getMessage());
                    errors.add(new UploadError(i + 1, "기타 처리 오류"));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("파일 읽기 실패", e);
        }
        log.info("파일 업로드 종료. 성공: {}, 실패: {}", success, errors.size());
        return new UploadResult(success, errors);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}