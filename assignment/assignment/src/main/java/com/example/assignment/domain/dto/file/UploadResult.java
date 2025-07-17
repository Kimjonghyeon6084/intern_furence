package com.example.assignment.domain.dto.file;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 업로드 결과 출력을 위한 DTO
 */
@Getter
@RequiredArgsConstructor
public class UploadResult {

    private final int successCount;

    private final List<UploadError> errors;

}
