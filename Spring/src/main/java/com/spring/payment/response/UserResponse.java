package com.spring.payment.response;

import com.spring.payment.eum.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "User response")
public class UserResponse {
	
	@Schema(description = "User ID" , example = "1")
	private Long id;
	
	@Schema(description = "이메일" , example = "Park@naver.com")
	private String email;
	
	@Schema(description = "이름" , example = "박창규")
	private String name;
	
	@Schema(description = "주소" , example = "경기도 평택시")
	private String address;
	
	private String registonDate;
	
	private String imgURL;
	
	@Schema(description = "권한", example = "ROLE_USER")
	private Role role;
	//@Schema(description = "작성한 상품 목록")
	//private List<ProductRequest> products;
}