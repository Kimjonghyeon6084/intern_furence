package com.example.assignment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UploadResult {
    private long successCount;
    private List<UploadError> errors;
}
