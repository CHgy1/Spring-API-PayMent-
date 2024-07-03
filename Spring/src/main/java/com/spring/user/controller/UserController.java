package com.spring.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.user.entity.UserEntity;
import com.spring.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "User API", description = "User API")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Operation(summary = "User 회원가입", description = "사용자 등록하고 데이터베이스에 저장")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "회원가입 성공"),
	        @ApiResponse(responseCode = "400", description = "잘못된 데이터 입력")
	})
	
	@PostMapping("/register")
	public ResponseEntity<UserEntity> Register(@RequestBody UserEntity userEntity){
		return ResponseEntity.ok(userService.Register(userEntity));
	}
	
	@Operation(summary = "User 전체 목록")
	@GetMapping
	public List<UserEntity> getAllUsers(){
		return userService.getAllUsers();
	}
	
    @Operation(summary = "User login", description = "Authenticates 인증 JWT 발급")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "로그인 인증 성공"),
        @ApiResponse(responseCode = "401", description = "로그인 인증 실패")
    })
    @PostMapping("/login")
    public void login(
        @Parameter(description = "Username", required = true) @RequestParam String username,
        @Parameter(description = "Password", required = true) @RequestParam String password) {

    }
	
}
