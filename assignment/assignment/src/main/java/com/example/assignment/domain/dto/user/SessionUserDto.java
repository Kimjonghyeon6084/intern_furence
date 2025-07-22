package com.example.assignment.domain.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class SessionUserDto {

    private String id;

    private String name;

}
