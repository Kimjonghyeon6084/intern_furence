package com.example.assignment;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class UserInsertGenerator {
    public static void main(String[] args) throws IOException {
        String[] koreanNames = {"홍길동", "김민수", "이철희", "박영희", "최수진", "오지현", "장동건", "한가인", "정우성", "김유나", "최강희", "이민호", "박지훈", "송혜교", "이수민"};
        String[] descSamples = {"호형호제못함", "다정다감", "성실함", "책임감있음", "적극적", "상냥함", "꼼꼼함", "열정적", "유머러스", "창의적"};

        Random rand = new Random();
        Set<String> usedIds = new HashSet<>();

        int totalCount = 570;
        int groupSizeMin = 2, groupSizeMax = 3;
        LocalDateTime baseDateTime = LocalDateTime.of(2025, 1, 1, 10, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (FileWriter fw = new FileWriter("insert_users.sql", false)) {
            int count = 0;
            int dayAdd = 0; // 1일씩 증가

            while (count < totalCount) {
                // 그룹 사이즈 2~3명
                int groupSize = groupSizeMin + rand.nextInt(groupSizeMax - groupSizeMin + 1);
                if (count + groupSize > totalCount) groupSize = totalCount - count; // 끝부분 조정

                // 그룹별 reg_date (1일씩 증가)
                LocalDateTime groupDate = baseDateTime.plusDays(dayAdd);
                String regDate = groupDate.format(formatter);
                dayAdd++; // 다음 그룹은 1일 증가

                for (int g = 0; g < groupSize && count < totalCount; g++, count++) {
                    // id: 5~8자리 대문자, 중복 방지
                    int idLen = rand.nextInt(4) + 5;
                    StringBuilder idBuilder = new StringBuilder();
                    for (int j = 0; j < idLen; j++)
                        idBuilder.append((char)('A' + rand.nextInt(26)));
                    String id = idBuilder.toString();
                    if (usedIds.contains(id)) { g--; continue; }
                    usedIds.add(id);

                    // pwd: 8~12자리 숫자
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

                    String sql = String.format(
                            "INSERT INTO t_user (id, pwd, name, level, \"desc\", reg_date) VALUES ('%s', '%s', '%s', '%c', %s, '%s');",
                            id, pwd, name, level, desc, regDate
                    );
                    fw.write(sql + "\n");
                }
            }
        }
        System.out.println("완료! insert_users.sql 파일이 생성되었습니다.");
    }
}
