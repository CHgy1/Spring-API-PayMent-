package com.spring.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.spring.user.jwt.JwtFilter;
import com.spring.user.jwt.JwtUtil;
import com.spring.user.jwt.LoginFilter;
import com.spring.user.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer{

	private AuthenticationConfiguration authenticationConfiguration;
	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService userDetailsService;

	public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtUtil jwtUtil,
			CustomUserDetailsService userDetailsService) {
		this.authenticationConfiguration = authenticationConfiguration;
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // CSRF 보호 비활성화
        http
                .csrf((auth) -> auth.disable());

        // Form 로그인 방식 비활성화
        http
                .formLogin((auth) -> auth.disable());

        // HTTP Basic 인증 방식 비활성화
        http
                .httpBasic((auth) -> auth.disable());

        // 요청 권한 설정
        http
                .authorizeHttpRequests((auth) -> auth
                		.requestMatchers("/login", "/", "/register", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/cart","/**").permitAll() // 로그인, 루트, 회원가입 경로는 모두 접근 허용
                        .requestMatchers("/admin").hasRole("ADMIN") // /admin 경로는 ADMIN 역할을 가진 사용자만 접근 허용
                        .anyRequest().authenticated()); // 그 외의 모든 요청은 인증 필요

        // JWTFilter를 LoginFilter 이전에 추가
        http
                .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);
        
        // LoginFilter를 UsernamePasswordAuthenticationFilter 위치에 추가
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

        // 세션 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션을 사용하지 않도록 설정 (무상태 방식)

        return http.build();
    }
	
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
