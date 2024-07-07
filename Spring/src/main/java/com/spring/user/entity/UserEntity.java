package com.spring.user.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.spring.payment.entity.Product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity	
@Getter
@Setter
@Table(name = "users")
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
	
	@OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference // 순환 참조
	private List<Product> products;
	
}
