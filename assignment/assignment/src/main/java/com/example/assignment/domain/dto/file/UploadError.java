package com.example.assignment.domain.dto.file;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UploadError {

    private final int lineCount;
    private final String errors;

}
