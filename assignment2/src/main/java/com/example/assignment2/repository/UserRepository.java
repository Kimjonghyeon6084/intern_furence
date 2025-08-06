package com.example.assignment2.repository;

import com.example.assignment2.domain.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>, UserRepositoryCustom {

}
