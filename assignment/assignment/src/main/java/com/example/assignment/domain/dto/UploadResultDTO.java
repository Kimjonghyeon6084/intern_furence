package com.example.assignment.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UploadResultDTO {

    private final String fileName;
    private final String userId;
    private final String message;
    private final boolean success;

}
