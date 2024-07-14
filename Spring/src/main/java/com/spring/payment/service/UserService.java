package com.spring.payment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.payment.domain.User;
import com.spring.payment.repository.UserRepository;
import com.spring.payment.request.UserRequest;
import com.spring.payment.response.UserResponse;
import com.spring.util.DateUtil;
import com.spring.util.ParserToken;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private ParserToken parserToken;
    
    private DateUtil dateUtil;
    
    // User 회원가입
    public UserResponse register(UserRequest userRequest) {
    	User user = conversionToEntity(userRequest);
    	user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
    	user.setRegistonDate(dateUtil.registonTime());
    	User saveUser = userRepository.save(user);
    	return conversionToResponse(saveUser);
    	
    }
    
    // User 전체 목록 조회
    public List<UserResponse> getAllUsers(){
    	List<User> users = userRepository.findAll();
    	return users.stream().map(this::conversionToResponse).collect(Collectors.toList());
    }
    
    // User 정보 수정
    public UserResponse updateUser(String token, String name, String address, String password) {
        User user = parserToken.conversionToToken(token);
         if(user == null) {
        	 new RuntimeException("유저 정보를 찾을 수 없음 : " + token);
         }

        user.setName(name);
        user.setAddress(address);

        if (password != null && !password.isEmpty()) {
            user.setPassword(bCryptPasswordEncoder.encode(password));
        }

        userRepository.save(user);
        return conversionToResponse(user);
    }
    
    
    
    
    // UserRequest -> User 변환
    public User conversionToEntity(UserRequest userRequest) {
    	User user = new User();
    	user.setId(userRequest.getId());
    	user.setEmail(userRequest.getEmail());
    	user.setPassword(userRequest.getPassword());
    	user.setName(userRequest.getName());
    	user.setRegistonDate(userRequest.getRegistonDate()); 
    	user.setAddress(userRequest.getAddress());
    	user.setRole(userRequest.getRole());
    	return user;
    }
    
    // User -> UserResponse 변환
    private UserResponse conversionToResponse(User user) {
    	UserResponse userResponse = new UserResponse();
    	userResponse.setId(user.getId());
    	userResponse.setEmail(user.getEmail());
    	userResponse.setName(user.getName());
    	userResponse.setRegistonDate(user.getRegistonDate());
    	userResponse.setAddress(user.getAddress());
    	userResponse.setRole(user.getRole());
    	userResponse.setImgURL(user.getImgURL());
        return userResponse;
    }

}
