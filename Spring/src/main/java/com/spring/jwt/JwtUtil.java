package com.spring.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;



@Component
public class JwtUtil {
	private SecretKey secretKey;
	
	public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
		this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
	}
	
	// Token에 포함된 Email 추출
	public String getEmail(String token) {
		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.get("email", String.class);
	}
	
	// Token 포함된 유저 권한 추출
	public String getRole(String token) {
		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.get("role", String.class);
	}
	
	// Token 포함된 만료기한 
	public Boolean isExpired(String token) {
		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getExpiration()
				.before(new Date());
	}
	
	/*
	 Token 발급
	 Token에 email, role 이 포함되어 있음
	 */
	public String createJwt(String email, String role, Long expiredMs) {
		return Jwts.builder()
				.claim("email", email)
				.claim("role", role)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expiredMs * 1000))
				.signWith(secretKey)
				.compact();
	}
}
