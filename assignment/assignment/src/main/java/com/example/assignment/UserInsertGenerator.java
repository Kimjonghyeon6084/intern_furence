package com.example.assignment;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class UserInsertGenerator {
    public static void main(String[] args) throws IOException {
        String[] koreanNames = {"홍길동", "김민수", "이철희", "박영희", "최수진", "오지현", "장동건", "한가인", "정우성", "김유나", "최강희", "이민호", "박지훈", "송혜교", "이수민"};
        String[] descSamples = {"호형호제못함", "다정다감", "성실함", "책임감있음", "적극적", "상냥함", "꼼꼼함", "열정적", "유머러스", "창의적"};

        Random rand = new Random();
        Set<String> usedIds = new HashSet<>();

        try (FileWriter fw = new FileWriter("insert_users.sql", false)) {
            int count = 0;
            while (count < 200) {
                // id: 3~6자리 대문자, 중복 방지
                int idLen = rand.nextInt(4) + 5;
                StringBuilder idBuilder = new StringBuilder();
                for (int j = 0; j < idLen; j++)
                    idBuilder.append((char)('A' + rand.nextInt(26)));
                String id = idBuilder.toString();

                if (usedIds.contains(id)) continue; // 중복 시 다시 생성
                usedIds.add(id);

                // pwd: 4~8자리 숫자
                int pwdLen = rand.nextInt(5) + 8;
                StringBuilder pwd = new StringBuilder();
                for (int j = 0; j < pwdLen; j++)
                    pwd.append(rand.nextInt(10));

                // name: 한글 이름 랜덤
                String name = koreanNames[rand.nextInt(koreanNames.length)];

                // level: A~F
                char level = (char)('A' + rand.nextInt(6));

                // desc: 50% NULL, 50% 샘플
                String desc;
                if (rand.nextBoolean()) {
                    desc = "NULL";
                } else {
                    desc = "'" + descSamples[rand.nextInt(descSamples.length)] + "'";
                }

                // reg_date: 2020-10-01 10:00:00부터 1분씩 증가
                int hour = 10 + (count / 60);
                int min = count % 60;
                String regDate = String.format("2020-10-01 %02d:%02d:00", hour, min);

                String sql = String.format(
                        "INSERT INTO t_user (id, pwd, name, level, \"desc\", reg_date) VALUES ('%s', '%s', '%s', '%c', %s, '%s');",
                        id, pwd, name, level, desc, regDate
                );

                fw.write(sql + "\n");
                count++;
            }
        }
        System.out.println("완료! insert_users.sql 파일이 생성되었습니다.");
    }
}