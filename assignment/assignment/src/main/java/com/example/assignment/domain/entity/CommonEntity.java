package com.example.assignment.domain.entity;

import java.sql.Timestamp;

public interface CommonEntity {

    String getId();
    String getPwd();
    String getName();
    String getLevel();
    String getDesc();
    Timestamp getReg_date();

}
