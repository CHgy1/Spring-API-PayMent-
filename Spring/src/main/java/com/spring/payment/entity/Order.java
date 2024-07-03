package com.spring.payment.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 사용자 ID
	@Column(nullable = false)
	private Long userId;

	// 주문 설명
	@Column(nullable = false)
	private String description;

	// 총 금액
	@Column(nullable = false)
	private double amount;
	
	// PG 주문 ID
    @Column(nullable = false)
    private String pgOrderId;

    // PG 키
    @Column(nullable = false)
    private String pgKey;

    // PG 상태
    @Column(nullable = false)
    private String pgStatus;

    // PG 재시도 횟수
    @Column(nullable = false)
    private int pgRetryCount;

    // 주문 시간
    @Column(nullable = false)
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductInOrder> products;
}
