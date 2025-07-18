package com.example.assignment.domain.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
public class SessionUserDto {

    private String id;

    private String name;

}
