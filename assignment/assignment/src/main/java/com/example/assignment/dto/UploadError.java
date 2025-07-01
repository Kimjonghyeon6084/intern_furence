package com.example.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadError {
    private long lineCount;
    private String text;
}
