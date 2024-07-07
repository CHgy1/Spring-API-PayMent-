package com.spring.payment.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        return productRepository.save(conversionEntity(productRequest, token));
    }

    public List<ProductRequest> getAllProducts() {
        List<Product> products = productRepository.findAll();
        for(Product p : products) {
            System.out.println(p.getName());
        }
        return conversionEntities(products);
    }

    private List<ProductRequest> conversionEntities(List<Product> products) {
        List<ProductRequest> productRequests = new ArrayList<>();
        for (Product product : products) {
            ProductRequest productRequest = conversionToRequest(product);
            productRequests.add(productRequest);
        }
        return productRequests;
    }

    private ProductRequest conversionToRequest(Product product) {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setId(product.getId());
        productRequest.setName(product.getName());
        productRequest.setPrice(product.getPrice());
        productRequest.setDescription(product.getDescription());
        productRequest.setStock(product.getStock());
        productRequest.setCreated_at(product.getCreated_at());

        return productRequest;
    }

    private Product conversionEntity(ProductRequest productRequest, String token) {
        Product product = new Product();
        UserEntity user = conversionToken(token);
        if (token != null) {
            product.setId(productRequest.getId());
            product.setName(productRequest.getName());
            product.setPrice(productRequest.getPrice());
            product.setDescription(productRequest.getDescription());
            product.setStock(productRequest.getStock());
            product.setCreated_at(dateFormatter());
            product.setUsers(user);
        } else {
            product.setId(productRequest.getId());
            product.setName(productRequest.getName());
            product.setPrice(productRequest.getPrice());
            product.setDescription(productRequest.getDescription());
            product.setStock(productRequest.getStock());
            product.setCreated_at(dateFormatter());
        }
        return product;
    }

    private UserEntity conversionToken(String token) {
        UserEntity user = userRepository.findByUsername(jwtUtil.getUsername(token.substring(7)));
        return user;
    }

    private String dateFormatter() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}
