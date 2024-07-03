package com.spring.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.payment.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
}


