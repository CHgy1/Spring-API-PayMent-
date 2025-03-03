package com.spring.payment.request;

import java.util.List;

import com.spring.payment.domain.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Product request DTO")
public class ProductRequest {

	
	private Long id;
	

	private String productName;

	private Double price;
	

	private String description;
	

	private Integer stock;
	
	
	private List<String> deleteImages; // 이미지 파일 리스트
	
	private User user;
}
