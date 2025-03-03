package com.spring.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.payment.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
}