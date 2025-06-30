package com.example.assignment.dto;

import lombok.Getter;

@Getter
public class UploadError {
    private int lineCount;
    @Getter
    private String text;

    public UploadError(int lineCount, String text) {
        this.lineCount = lineCount;
        this.text = text;
    }

    public int getLineNumber() {
        return lineCount;
    }

}
