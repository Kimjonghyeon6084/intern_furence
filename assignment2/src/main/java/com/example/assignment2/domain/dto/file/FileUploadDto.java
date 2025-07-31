package com.example.assignment2.domain.dto.file;

import com.example.assignment2.common.exception.CustomException;
import com.example.assignment2.common.exception.ErrorCode;
import com.example.assignment2.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 파일 업로드시 필요한 DTO
 */
@Getter
@Builder
public class FileUploadDto {

    private String id;

    private String pwd;

    private String name;

    private String level;

    private String desc;

    private String regDate;

    /**
     * "/"로 잘라서 나온 조각을 담는 메서드
     * @param parts
     * @return dto FileUploadDto
     */
    public static FileUploadDto fromParts(String[] parts){
        if (parts.length == 5){
            return FileUploadDto.builder()
                    .id(parts[0])
                    .pwd(parts[1])
                    .name(parts[2])
                    .level(parts[3])
                    .desc(null)
                    .regDate(parts[4])
                    .build();
        } else if (parts.length == 6) {
            return FileUploadDto.builder()
                    .id(parts[0])
                    .pwd(parts[1])
                    .name(parts[2])
                    .level(parts[3])
                    .desc(parts[4])
                    .regDate(parts[5])
                    .build();
        } else {
            throw new CustomException(ErrorCode.FIELD_COUNT_INVALID, ErrorCode.FIELD_COUNT_INVALID.getMessage());
        }
    }
    /**
     * 각 필드의 값 형식 검사 및 null 체크
    **/
    public void validate(int lineNumber) {

        if (id == null || id.isBlank())
            throw new CustomException(ErrorCode.ID_EMPTY, ErrorCode.ID_EMPTY.getMessage() + "라인 : " + lineNumber);
        if (!id.matches("^[A-Z]+$"))
            throw new CustomException(ErrorCode.ID_NOT_UPPERCASE, ErrorCode.ID_NOT_UPPERCASE.getMessage() + "라인: " + lineNumber);

        if (pwd == null || pwd.isBlank())
            throw new CustomException(ErrorCode.PWD_EMPTY, ErrorCode.PWD_EMPTY.getMessage() + "라인: " + lineNumber);
        if (!pwd.matches("^[0-9]+$"))
            throw new CustomException(ErrorCode.PWD_NOT_NUMERIC, ErrorCode.PWD_NOT_NUMERIC.getMessage()  + "라인: " + lineNumber);;

        if (name == null || name.isBlank())
            throw new CustomException(ErrorCode.NAME_EMPTY, ErrorCode.NAME_EMPTY.getMessage() + "라인: " + lineNumber);
        if (!name.matches("^[가-힣]+$"))
            throw new CustomException(ErrorCode.NAME_NOT_KOREAN, ErrorCode.NAME_NOT_KOREAN.getMessage()+ "라인: " + lineNumber);

        if (level == null || level.isBlank())
            throw new CustomException(ErrorCode.LEVEL_EMPTY, ErrorCode.LEVEL_EMPTY.getMessage()+ "라인: " + lineNumber);
        if (!level.matches("^[A-F]$"))
            throw new CustomException(ErrorCode.LEVEL_INVALID, ErrorCode.LEVEL_INVALID.getMessage()+ "라인: " + lineNumber);

        if (regDate == null || regDate.isBlank())
            throw new CustomException(ErrorCode.REGDATE_EMPTY, ErrorCode.REGDATE_EMPTY.getMessage() + "라인: " + lineNumber);
        if (!regDate.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$"))
            throw new CustomException(ErrorCode.REGDATE_INVALID, ErrorCode.REGDATE_INVALID.getMessage()+ "라인: " + lineNumber);
    }

    /**
     * 최종 검사 후 entity에 담는 메서드
     * @return User
     */
    public User toEntity() {
        return User.builder()
                .id(id)
                .pwd(pwd)
                .name(name)
                .level(level)
                .desc(desc)
                .regDate(LocalDateTime.parse(regDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }
}
