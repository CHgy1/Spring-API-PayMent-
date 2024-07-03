package com.spring.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsByUsername(String username);

    UserEntity findByUsername(String username);
}
