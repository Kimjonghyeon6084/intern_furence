package com.example.assignment.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UploadError {
    private int lineCount;
    private String errors;

    public UploadError(int lineCount, String errors) {
        this.lineCount = lineCount;
        this.errors = errors;
    }
}
