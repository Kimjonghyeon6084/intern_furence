package com.example.assignment.domain.dto.file;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 파일 업로드중 에러 발생 시 담는 DTO
 */
@Getter
@Builder
@RequiredArgsConstructor
public class UploadErrorResponseDto {

    private final int lineCount;

    private final String errors;

}
