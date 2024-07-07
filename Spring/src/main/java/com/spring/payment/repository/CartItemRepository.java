package com.spring.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.payment.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
