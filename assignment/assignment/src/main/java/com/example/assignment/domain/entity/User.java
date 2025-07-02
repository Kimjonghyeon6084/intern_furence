package com.example.assignment.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity(name = "t_user")
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
    private Timestamp reg_date;
}
