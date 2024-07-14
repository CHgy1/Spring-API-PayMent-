package com.spring.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.payment.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
