package com.spring.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.user.entity.UserEntity;
import com.spring.user.jwt.JwtUtil;
import com.spring.user.jwt.LoginFilter;
import com.spring.user.request.UserRequest;
import com.spring.user.response.UserResponse;
import com.spring.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/users")
@Tag(name = "User API", description = "User management APIs")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
    @Operation(summary = "사용자 회원가입", description = "제공된 정보로 사용자 등록하세요.")
    @PostMapping("/register")
	public ResponseEntity<UserResponse> Register(@RequestBody UserRequest userRequest){
		return ResponseEntity.ok(userService.register(userRequest));
	}
	
    @Operation(summary = "전체 사용자 조회", description = "등록된 모든 사용자를 조회합니다.")
    @GetMapping
	public ResponseEntity<List<UserResponse>> getAllUsers(){
		List<UserResponse> userResponses = userService.getAllUsers();
		return ResponseEntity.ok(userResponses);
	}
	
    @Operation(summary = "User login", description = "Authenticates 인증 JWT 발급")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "로그인 인증 성공"), 
        @ApiResponse(responseCode = "401", description = "로그인 인증 실패")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.createJwt(authentication.getName(), authentication.getAuthorities().iterator().next().getAuthority(), 60 * 60 * 10L);
        
        
        return ResponseEntity.ok(jwt);
    }
	

}
