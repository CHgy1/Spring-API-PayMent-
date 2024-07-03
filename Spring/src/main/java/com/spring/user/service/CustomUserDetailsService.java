package com.spring.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.user.dto.CustomUserDetails;
import com.spring.user.entity.UserEntity;
import com.spring.user.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("username : " + username);
		UserEntity user = userRepository.findByUsername(username);
		
		if(user != null) {
			System.out.println("UserDetailsService : " + user);
			return new CustomUserDetails(user);
		}
		
		return null;
	}
	


}
