package com.spring.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.payment.domain.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
