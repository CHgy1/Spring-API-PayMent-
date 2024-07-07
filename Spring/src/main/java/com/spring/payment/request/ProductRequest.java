package com.spring.payment.request;

import com.spring.user.entity.UserEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Product request DTO")
public class ProductRequest {
	
	@Schema(description = "Product Id", example = "1")
	private Long id;
	
	@Schema(description = "상품 이름", example = "test상품")
	private String name;
	
	@Schema(description = "상품 가격", example = "999")
	private double price;
	
	@Schema(description = "상품 설명", example = "test 상품 입니다.")
	private String description;
	
	@Schema(description = "상품 수량", example = "9999")
	private int stock;
	
	@Schema(description = "상품 등록 날짜", example = "2024-07-05 16:27:53")
	private String created_at;
	

}
