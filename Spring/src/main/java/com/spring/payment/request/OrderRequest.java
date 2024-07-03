package com.spring.payment.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
	private Long userId;
    private String description;
    private List<ProductInOrderRequest> products;
}
