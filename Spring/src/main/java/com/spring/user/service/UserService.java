package com.spring.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.payment.request.ProductRequest;
import com.spring.user.entity.UserEntity;
import com.spring.user.repository.UserRepository;
import com.spring.user.request.UserRequest;
import com.spring.user.response.UserResponse;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public UserResponse register(UserRequest userRequest) {
        UserEntity userEntity = conversionToEntity(userRequest);
        userEntity.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        UserEntity savedUser = userRepository.save(userEntity);
        return conversionToResponse(savedUser);
    }
    
    public List<UserResponse> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(this::conversionToResponse).collect(Collectors.toList());
    }
    
    private UserEntity conversionToEntity(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRequest.getUsername());
        userEntity.setPassword(userRequest.getPassword());
        userEntity.setNickname(userRequest.getNickname());
        userEntity.setRegistonDate(userRequest.getRegistonDate());
        userEntity.setAddress(userRequest.getAddress());
        userEntity.setRole(userRequest.getRole());
        return userEntity;
    }

    private UserResponse conversionToResponse(UserEntity userEntity) {
    	UserResponse userResponse = new UserResponse();
    	userResponse.setId(userEntity.getId());
    	userResponse.setUsername(userEntity.getUsername());
    	userResponse.setNickname(userEntity.getNickname());
    	userResponse.setRegistonDate(userEntity.getRegistonDate());
    	userResponse.setAddress(userEntity.getAddress());
    	userResponse.setRole(userEntity.getRole());
    	if (userEntity.getProducts() != null) {
            List<ProductRequest> products = userEntity.getProducts().stream()
                .map(product -> {
                    ProductRequest productResponse = new ProductRequest();
                    productResponse.setId(product.getId());
                    productResponse.setName(product.getName());
                    productResponse.setPrice(product.getPrice());
                    productResponse.setDescription(product.getDescription());
                    productResponse.setStock(product.getStock());
                    productResponse.setCreated_at(product.getCreated_at());
                    return productResponse;
                }).collect(Collectors.toList());
            userResponse.setProducts(products);
        }
        return userResponse;
    }
    
    public UserEntity findByUsername(String username) {
    	return userRepository.findByUsername(username);
    }
}
