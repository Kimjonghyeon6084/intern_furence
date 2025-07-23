package com.example.assignment.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {

    //entityManager 주입
    @PersistenceContext
    private EntityManager entityManager;

    //JPAQueryFactory를 객체로 만드는 곳에 스프링에서 알아서 di 해주게 함.
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
