package com.example.assignment2.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity(name = "t_user")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

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

    public User(String id, String pwd, String name, String level, String desc, LocalDateTime regDate) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.level = level;
        this.desc = desc;
        this.regDate = regDate;
    }
}
