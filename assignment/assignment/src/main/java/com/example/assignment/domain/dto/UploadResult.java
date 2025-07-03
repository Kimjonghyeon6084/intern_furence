package com.example.assignment.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class UploadResult {
    private int successCount;
    private List<UploadError> errors;

    public UploadResult(int successCount, List<UploadError> errors) {
        this.successCount = successCount;
        this.errors = errors;
    }
}
