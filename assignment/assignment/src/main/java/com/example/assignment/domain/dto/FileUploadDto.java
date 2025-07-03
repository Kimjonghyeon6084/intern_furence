package com.example.assignment.domain.dto;

import com.example.assignment.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Getter
//@RequiredArgsConstructor
@AllArgsConstructor
public class FileUploadDto {
    private String id;
    private String pwd;
    private String name;
    private String level;
    private String desc;
    private String regDate;

    public static FileUploadDto fromParts(String[] parts){
        return new FileUploadDto(parts[0],parts[1],parts[2],parts[3],parts[4],parts[5]);
    }

    public boolean isValid() {
        if (id == null || id.isBlank()) return false;
        if (pwd == null || pwd.isBlank()) return false;
        if (name == null || name.isBlank()) return false;
        if (level == null || level.isBlank()) return false;
        if (regDate == null || regDate.isBlank()) return false;
        return true;
    }

    public User toEntity() {
        return User.builder()
                .id(id)
                .pwd(pwd)
                .name(name)
                .level(level)
                .desc(desc)
                .reg_date(Timestamp.valueOf(regDate))
                .build();
    }
}
