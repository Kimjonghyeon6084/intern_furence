package com.example.assignment.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Entity(name = "t_user")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements CommonEntity {
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
    private Timestamp reg_date;

    public User(String id, String pwd, String name,
                    String level, String desc, Timestamp reg_date) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.level = level;
        this.desc = desc;
        this.reg_date = reg_date;
    }
}
