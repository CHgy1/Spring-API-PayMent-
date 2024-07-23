package com.spring.payment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.spring.payment.domain.User;
import com.spring.payment.eum.Role;
import com.spring.payment.repository.UserRepository;
import com.spring.payment.request.UserRequest;
import com.spring.payment.response.UserResponse;
import com.spring.util.DateUtil;
import com.spring.util.ParserToken;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private ParserToken parserToken;

    @Mock
    private DateUtil dateUtil;

    private UserRequest userRequest;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userRequest = new UserRequest();
        userRequest.setName("testuser");
        userRequest.setPassword("password");
        userRequest.setEmail("testuser@example.com");
        userRequest.setAddress("123 Test St");
        userRequest.setRole(Role.ROLE_USER);

        user = new User();
        user.setId(1L);
        user.setName("testuser");
        user.setPassword("encodedPassword");
        user.setEmail("testuser@example.com");
        user.setAddress("123 Test St");
        user.setRole(Role.ROLE_USER);
    }

    @Test
    void testRegister() {
        when(bCryptPasswordEncoder.encode(userRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(dateUtil.registonTime()).thenReturn("2024-07-23T10:00:00");

        UserResponse userResponse = userService.register(userRequest);

        assertNotNull(userResponse);
        assertEquals("testuser", userResponse.getName());
        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        List<UserResponse> userResponses = userService.getAllUsers();

        assertNotNull(userResponses);
        assertEquals(1, userResponses.size());
        assertEquals("testuser", userResponses.get(0).getName());
    }

    @Test
    void testUpdateUser() {
        String token = "mockToken";
        when(parserToken.conversionToToken(token)).thenReturn(user);
        when(bCryptPasswordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse userResponse = userService.updateUser(token, "newName", "newAddress", "newPassword");

        assertNotNull(userResponse);
        assertEquals("newName", userResponse.getName());
        assertEquals("newAddress", userResponse.getAddress());
        System.out.println(user.getPassword());
        assertEquals("encodedNewPassword", user.getPassword()); // Assuming bcrypt returns "encodedNewPassword"
    }
    
    
}
