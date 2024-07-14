package com.spring.payment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.payment.request.ProductRequest;
import com.spring.payment.response.ProductResponse;
import com.spring.payment.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/create",consumes = {"multipart/form-data"})
    public ResponseEntity<ProductResponse> createProduct(
            @RequestPart("product") ProductRequest productRequest,
            @RequestPart("images") List<MultipartFile> images,
            @RequestHeader(value = "Authorization", required = false) String token) {
        ProductResponse productResponse = productService.createProduct(productRequest, images, token);
        return ResponseEntity.ok(productResponse);
    }
    
    @GetMapping("/list")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/details/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id){
    	return ResponseEntity.ok(productService.getProduct(id));
    }
     
    // 제품 수정 엔드포인트
    @PutMapping(value = "/update", consumes = {"multipart/form-data"})
    public ResponseEntity<ProductResponse> updateProduct(
            @RequestPart("product") ProductRequest productRequest,
            @RequestPart("images") List<MultipartFile> images,
            @RequestHeader("Authorization") String token) {
        ProductResponse response = productService.updateProduct(productRequest, images, token);
        return ResponseEntity.ok(response);
    }
}
