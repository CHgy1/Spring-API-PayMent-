package com.spring.payment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.spring.payment.entity.Product;
import com.spring.payment.request.ProductRequest;
import com.spring.payment.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Product API", description = "상품 CRUD")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Operation(summary = "상품 등록")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "상품 등록 성공"),
	        @ApiResponse(responseCode = "400", description = "잘못된 데이터 입력")
	})
	@PostMapping("/product/add")
	public ResponseEntity<Product> createProduct(@RequestBody ProductRequest productRequest, @RequestHeader("Authorization")String token){
		return ResponseEntity.ok(productService.createProduct(productRequest, token));
	}
	
	@Operation(summary = "상품 전체 목록")
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProduct(){
		return ResponseEntity.ok(productService.getAllProducts());
	}

}
