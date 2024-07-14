package com.spring.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.payment.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    User findByEmail(String email);
}
