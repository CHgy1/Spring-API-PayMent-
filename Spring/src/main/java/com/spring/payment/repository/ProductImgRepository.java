package com.spring.payment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.payment.domain.Product;
import com.spring.payment.domain.ProductImg;

public interface ProductImgRepository extends JpaRepository<ProductImg, Long> {
	List<ProductImg> findByProduct(Product product);
}
