package com.spring.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.user.entity.UserEntity;
import com.spring.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	public UserEntity Register(UserEntity userEntity) {
		userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
		return userRepository.save(userEntity);
	}
	
	public List<UserEntity> getAllUsers(){
		return userRepository.findAll();
	}
}
