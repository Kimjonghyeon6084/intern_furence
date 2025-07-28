package com.example.assignment.domain.entity;

import com.example.assignment.domain.dto.UserDtoBase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.*;

import java.sql.Timestamp;

/**
 * User Entity
 */
@Getter
@Entity(name = "t_user")
@Builder
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
    private Timestamp regDate;

    public User(String id, String pwd, String name,
                    String level, String desc, Timestamp regDate) {

        this.id = id;

        this.pwd = pwd;

        this.name = name;

        this.level = level;

        this.desc = desc;

        this.regDate = regDate;
    }
}
