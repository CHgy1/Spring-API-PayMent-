package com.spring.payment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.payment.entity.Product;
import com.spring.payment.repository.ProductRepository;
import com.spring.payment.request.ProductRequest;
import com.spring.user.entity.UserEntity;
import com.spring.user.jwt.JwtUtil;
import com.spring.user.repository.UserRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserRepository userRepository;
	
	
	public Product createProduct(ProductRequest productRequest, String token) {
		/*
		 * String jwtToken = token.substring(7); // Bearer 제거 
		 * String username = jwtUtil.getUsername(jwtToken);
		 */
		UserEntity user =
		userRepository.findByUsername
		(jwtUtil.getUsername(token.substring(7)));
		
		Product product = 
				new Product
				(productRequest.getName(), productRequest.getPrice(), user);
		return productRepository.save(product);
	}
	
	
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
}
