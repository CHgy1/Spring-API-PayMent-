package com.spring.payment.request;

import com.spring.payment.eum.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "User request")
public class UserRequest {

	@Schema(description = "User ID" , example = "1")
	private Long id;
	
	@NotNull
	@Schema(description = "이메일" , example = "Park@naver.com")
	private String email;
	
	@NotNull
	@Schema(description = "비밀번호" , example = "1234")
	private String password;
	
	@NotNull
	@Schema(description = "이름" , example = "박창규")
	private String name;
	
	@NotNull
	@Schema(description = "주소" , example = "경기도 평택시")
	private String address;
	
	private String registonDate;
	
	private String imgURL;
	
	private Role role;
}
