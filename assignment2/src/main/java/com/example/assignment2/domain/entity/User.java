package com.example.assignment2.domain.entity;

import com.example.assignment2.domain.dto.UserDtoBase;
import com.example.assignment2.domain.dto.user.info.UserEditRequestDto;
import com.example.assignment2.domain.dto.user.info.UserEditResponseDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Entity(name = "t_user")
@Builder
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDtoBase {

    @Id
    @Column
    private String id;

    @Column
    private String pwd;

    @Column
    private String name;

    @Column
    private String level;

    @Column(name = "\"desc\"")
    private String desc;

    @Column
    private LocalDateTime regDate;

    @Column
    private LocalDateTime modDate;

    public User(String id, String pwd, String name, String level, String desc, LocalDateTime regDate, LocalDateTime modDate) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.level = level;
        this.desc = desc;
        this.regDate = regDate;
        this.modDate = modDate;
    }

    /**
     * 회원정보수정시 사용하는 메서드
     * @param dto
     */
    public void updateFrom(UserEditRequestDto dto) {
        if (dto.getPwd() != null && !dto.getPwd().isBlank()) {
            this.pwd = dto.getPwd();
        }
        if (dto.getName() != null) {
            this.name = dto.getName();
        }
        if (dto.getLevel() != null) {
            this.level = dto.getLevel();
        }
        if (dto.getDesc() != null) {
            this.desc = dto.getDesc();
        }
        if (dto.getModDate() != null) {
            this.modDate = dto.getModDate();
        }
    }
}
