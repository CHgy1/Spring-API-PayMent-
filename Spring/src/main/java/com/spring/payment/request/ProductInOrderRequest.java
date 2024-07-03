package com.spring.payment.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInOrderRequest {
	private Long productId;
	private Integer quantity;
}
