package com.example.assignment.domain.valid;

import jakarta.validation.GroupSequence;

import static com.example.assignment.domain.valid.ValidationGroup.*;

/**
 * 검증순서 정하는 인터페이스
 */
@GroupSequence({NotBlankGroup.class, SizeGroup.class})
public interface ValidationSequence {
}
