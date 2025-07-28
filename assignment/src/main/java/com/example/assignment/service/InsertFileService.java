package com.example.assignment.service;

import com.example.assignment.domain.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * .dbfile 파일에 있는 한 줄씩 insert하는 클래스
 */
@Service
public class InsertFileService {

    @PersistenceContext
    private EntityManager em;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertFile(User user) {
        em.persist(user);
        em.flush();
    }
}
