package com.example.assignment2.domain.entity;

import com.example.assignment2.domain.dto.UserDtoBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Entity(name = "t_user")
@Builder
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDtoBase {

    @Id
    @Column
    private String id;

    @Column
    private String pwd;

    @Column
    private String name;

    @Column
    private String level;

    @Column(name = "\"desc\"")
    private String desc;

    @Column
    private LocalDateTime regDate;

    @Column
    private LocalDateTime modDate;

    public User(String id, String pwd, String name, String level, String desc, LocalDateTime regDate, LocalDateTime modDate) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.level = level;
        this.desc = desc;
        this.regDate = regDate;
        this.modDate = modDate;
    }
}
