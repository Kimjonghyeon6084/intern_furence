package com.example.assignment2.domain.dto.user.login;

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
