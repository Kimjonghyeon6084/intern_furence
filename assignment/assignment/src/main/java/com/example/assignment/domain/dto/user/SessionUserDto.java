package com.example.assignment.domain.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class SessionUserDto implements Serializable {

    private final String id;

    private final String name;

}
