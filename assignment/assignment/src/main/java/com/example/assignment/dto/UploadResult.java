package com.example.assignment.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UploadResult {
    private int successCount;
    private List<UploadError> errors;

    public UploadResult(int successCount, List<UploadError> errors){
        this.successCount = successCount;
        this.errors = errors;
    }

    public int getFailureCount() {
        return errors.size();
    }
}
