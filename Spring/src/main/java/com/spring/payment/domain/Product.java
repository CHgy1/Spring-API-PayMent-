package com.spring.payment.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String productName;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private Double price;
	
	@Column(nullable = false)
	private Integer stock;
	
	private String create_at;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference // 순환 참조
	private User users;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference // 순환 참조
	private List<ProductImg> images;
	 
    public Product(String productName, double price, User users) {
    	this.productName = productName;
    	this.price = price;
    	this.users = users;
    }
    
    public Product() {};
}
