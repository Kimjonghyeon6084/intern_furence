package com.example.assignment.domain.dto.file;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class UploadResult {

    private final int successCount;
    private final List<UploadError> errors;

}
