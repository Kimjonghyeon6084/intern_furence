package com.example.assignment.domain.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SessionUserDto {

    private String id;

    private String name;

}
