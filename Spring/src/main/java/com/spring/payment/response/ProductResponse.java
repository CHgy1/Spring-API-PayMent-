package com.spring.payment.response;

import java.util.List;

import com.spring.payment.domain.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Product response DTO")
public class ProductResponse {
	private Long id;
	
	private String productName;
	private Double price;
	
	private String description;
	
	private Integer stock;
	
	private String created_at;
	
	private List<String> images;
	
	private User users;

}
