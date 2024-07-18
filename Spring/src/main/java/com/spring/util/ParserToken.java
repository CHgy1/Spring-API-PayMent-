package com.spring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.spring.jwt.JwtUtil;
import com.spring.payment.domain.User;
import com.spring.payment.repository.UserRepository;

@Configuration
public class ParserToken {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtUtil jwtUtil;
	
	public User conversionToToken(String token) {
		System.out.println(token);
   	 User user = userRepository.findByEmail(jwtUtil.getEmail(token.substring(7)));
   	 System.out.println("ParserToken Username : " + user.getEmail());
   	 if (user == null) {
   		 throw new RuntimeException("잘못된 정보 입니다.");
   	 }
   	 return user;
   }
}
