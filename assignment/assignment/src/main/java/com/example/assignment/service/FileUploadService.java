package com.example.assignment.service;

import com.example.assignment.domain.dto.UploadError;
import com.example.assignment.domain.dto.UploadResult;
import com.example.assignment.domain.entity.User;
import com.example.assignment.repository.UserRepository;

import jakarta.persistence.PersistenceException;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FileUploadService {

    private final UserRepository userRepository;
    private final InsertOneService insertOneService;
    private static final long INITIAL_SUCCESS_COUNT = 0L;

    // pk값이 중복되는 것은 못 들어가게 하기 위해 dbfile의 각각의 레코드에 트랜잭션을 걸어 확인 후 insert
    public UploadResult processFile(MultipartFile file) {

        List<String> lines;
        long success = INITIAL_SUCCESS_COUNT;
        List<UploadError> errors = new ArrayList<>();

        try {
            lines = new BufferedReader(new InputStreamReader(file.getInputStream()))
                    .lines().toList();

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                try {
                    String[] parts = line.split("/");

                    if (isInvalidParts(parts)) {
                        errors.add(new UploadError(i + 1, line));
                        continue;
                    }

                    User user = insertData(parts);
                    // dbflie에 있는 한줄한줄씩 등록
                    try {
                        insertOneService.insertFileOne(user);
                        success++;
                    } catch (PersistenceException e){
                        errors.add(new UploadError(i + 1, line));
                    }

                } catch (Exception e) {
                    System.out.println("실패 라인 " + (i + 1) + ": " + line);
                    e.printStackTrace();
                    errors.add(new UploadError(i + 1, line));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("파일 읽기 실패", e);
        }

        return new UploadResult(success, errors);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    private boolean isInvalidParts(String[] parts) {
        // parts 개수 체크
        if (parts.length != 6){
            return true;
        }

        // 4번째 빼고 공백 체크
        for (int j = 0; j < parts.length; j++) {
            if (j == 4) continue;
            if (parts[j] == null || parts[j].trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private User insertData(String[] parts) {
        User user = new User();
        user.setId(parts[0]);
        user.setPwd(parts[1]);
        user.setName(parts[2]);
        user.setLevel(parts[3]);
        user.setDesc(parts[4]);
        user.setReg_date(Timestamp.valueOf(parts[5]));
        return user;
    }
}