package com.spring.payment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.jwt.JwtUtil;
import com.spring.payment.request.UserRequest;
import com.spring.payment.response.UserResponse;
import com.spring.payment.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

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
    
    @Operation(summary = "사용자 전체 목록", description = "등록된 모든 사용자를 조회합니다")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
    	List<UserResponse> userResponse = userService.getAllUsers();
    	return ResponseEntity.ok(userResponse);
    }
    @Operation(summary = "사용자 정보 수정", description = "사용자 정보를 수정 합니다.", security = @SecurityRequirement(name = "bearer-key"))
    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUser(
    		@RequestHeader(value = "Authorization", required = false) String token, 
            @RequestParam String name, 
            @RequestParam String address, 
            @RequestParam(required = false) String password) {
        UserResponse response = userService.updateUser(token, name, address, password);
        return ResponseEntity.ok(response);
    }
    
    
    @Operation(summary = "로그인", description = "Authecticates 인증 JWT 발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 인증 성공"), 
            @ApiResponse(responseCode = "401", description = "로그인 인증 실패")
        })
        @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.createJwt(authentication.getName(), authentication.getAuthorities().iterator().next().getAuthority(), 60 * 60 * 10L);
        
        
        return ResponseEntity.ok(jwt);
    }
}
