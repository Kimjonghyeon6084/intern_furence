package com.example.assignment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadError {
    private long lineCount;
    private String text;
}
