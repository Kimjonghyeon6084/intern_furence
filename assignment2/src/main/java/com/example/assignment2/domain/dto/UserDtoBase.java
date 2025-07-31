package com.example.assignment2.domain.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public interface UserDtoBase {
    String getId();

    String getName();

    String getPwd();

    String getLevel();

    String getDesc();

    LocalDateTime getRegDate();
}
