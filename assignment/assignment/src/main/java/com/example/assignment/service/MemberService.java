package com.example.assignment.service;

import com.example.assignment.dto.UploadError;
import com.example.assignment.dto.UploadResult;
import com.example.assignment.entity.User;
import com.example.assignment.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    private final UserRepository userRepository;
    @PersistenceContext
    private EntityManager em;

    public MemberService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertOne(User user) {
        em.persist(user);
        em.flush();
    }

    // pk값이 중복되는 것은 못 들어가게 하기 위해 dbfile의 각각의 레코드에 트랜잭션을 걸어 확인 후 insert
    public UploadResult processFile(MultipartFile file) {
        List<String> lines;
        int success = 0;
        List<UploadError> errors = new ArrayList<>();

        try {
            lines = new BufferedReader(new InputStreamReader(file.getInputStream()))
                    .lines().toList();

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                try {
                    String[] parts = line.split("/");

                    // parts가 desc 부분만 빼고 공백이 있을 경우 실패처리
                    boolean ifBlank = false;

                    for(int j = 0; j < parts.length; j++){
                        if(j == 4) continue;

                        if (parts[j] == null || parts[j].trim().isEmpty()){
                            ifBlank = true;
                            break;
                        }
                    }
                    if (ifBlank) {
                        errors.add(new UploadError(i + 1, line));
                        continue;
                    }

                    User user = new User();
                    user.setId(parts[0]);
                    user.setPwd(parts[1]);
                    user.setName(parts[2]);
                    user.setLevel(parts[3]);
                    user.setDesc(parts[4]);
                    user.setReg_date(Timestamp.valueOf(parts[5]));

                    try {
                        insertOne(user);
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
}