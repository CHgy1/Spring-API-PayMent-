package com.spring.payment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.spring.user.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;
    
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference // 순환 참조
    private UserEntity users;
    // ProductInOrder와의 양방향 관계 설정
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<ProductInOrder> productInOrders;
    
    
    public Product(String name, double price, UserEntity users) {
    	this.name = name;
    	this.price = price;
    	this.users = users;
    }
    public Product() {};
}