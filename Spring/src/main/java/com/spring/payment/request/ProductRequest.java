package com.spring.payment.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
	private Long id;
	private String name;
	private double price;
}
