package com.example.assignment;

import com.example.assignment.entity.User;
import com.example.assignment.repository.UserRepository;
import com.example.assignment.service.MemberService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

@SpringBootTest
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("파일 업로드 케이스 테스트")
    @Transactional
    public void insertFile(){
        //given
        String userID = "ABC";
        String pwd = "testpassword";
        String name = "testing";
        String level = "1";
        String desc = "test";
        Timestamp reg_date = Timestamp.valueOf("2020-10-01 10:00:00");


        //when


        // then
    }

}
