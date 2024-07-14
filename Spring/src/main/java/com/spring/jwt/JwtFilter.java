package com.spring.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.dto.CustomUserDetails;
import com.spring.payment.domain.User;
import com.spring.payment.eum.Role;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Slf4j
public class JwtFilter extends OncePerRequestFilter{

	private final JwtUtil jwtUtil;
	
	public JwtFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}
	private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// request에서 Authorization 헤더 찾기
		String authorization = request.getHeader("Authorization");
		logger.info("Authorization Header: {}", authorization);
		// Authorization 헤더 검증
		if(authorization == null || !authorization.startsWith("Bearer ")) {
			log.info("Authorization = null");
			filterChain.doFilter(request, response);
			// 조건이 해당되면 메소드 종료
			return;
		}
		
		String token = authorization.split(" ")[1];
		
		// 토큰 만료기간 검증
		if(jwtUtil.isExpired(token)) {
			log.info("만료된 Token");
			filterChain.doFilter(request, response);
			// 조건이 해당되면 메소드 종료
			return;
		}
		
		String email = jwtUtil.getEmail(token);
		String roleString = jwtUtil.getRole(token);
		Role role = Role.valueOf(roleString);
		
		User user = new User();
		user.setEmail(email);
		user.setPassword("temppassword");
		user.setRole(role);
		
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);
		
		filterChain.doFilter(request, response);
	}
}
