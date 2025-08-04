package com.example.assignment2.domain.dto.user;

import com.example.assignment2.common.customAnnotation.CheckDateRangeValid;
import lombok.Getter;
import lombok.Setter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@CheckDateRangeValid(
        startField = "startRegDate",
        endField = "endRegDate"
)
public class UserListRequestDto {

    private String id;

    private String name;

    private String level;

    private String desc;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startRegDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endRegDate;
}
