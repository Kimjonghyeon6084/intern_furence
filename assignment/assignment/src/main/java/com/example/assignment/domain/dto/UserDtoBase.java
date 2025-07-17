package com.example.assignment.domain.dto;

import java.sql.Timestamp;

public interface UserDtoBase {
    String getId();

    String getName();

    String getPwd();

    String getLevel();

    String getDesc();

    Timestamp getRegDate();
}
