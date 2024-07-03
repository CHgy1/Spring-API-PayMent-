package com.spring.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity	
@Getter
@Setter
public class UserEntity {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	
	private String password;
	
	private String nickname;
	
	private String registonDate;
	
	private String address;
	
	@Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER; 
	
	
}
