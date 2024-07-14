package com.spring.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.payment.domain.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}