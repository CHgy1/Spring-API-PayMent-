//package com.spring.controller;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.Arrays;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.restdocs.RestDocumentationContextProvider;
//import org.springframework.restdocs.RestDocumentationExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import com.spring.user.controller.UserController;
//import com.spring.user.entity.Role;
//import com.spring.user.entity.UserEntity;
//import com.spring.user.jwt.JwtUtil;
//import com.spring.user.service.CustomUserDetailsService;
//import com.spring.user.service.UserService;
//
//@ExtendWith(RestDocumentationExtension.class)
//@WebMvcTest(UserController.class)
//@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
//public class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private JwtUtil jwtUtil;  // 추가된 부분
//    @MockBean
//    private CustomUserDetailsService customUserDetailsService;
//    @MockBean
//    private UserService userService;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @BeforeEach
//    public void setUp(RestDocumentationContextProvider restDocumentation) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
//                .apply(documentationConfiguration(restDocumentation))
//                .alwaysDo(document("{class-name}/{method-name}",
//                        preprocessRequest(),
//                        preprocessResponse()))
//                .build();
//    }
//
//    @Test
//    public void testRegister() throws Exception {
//        String userJson = "{\"username\":\"testuser\", \"password\":\"password\", \"nickname\":\"nickname\", \"registonDate\":\"2024-07-03\", \"role\":\"ROLE_USER\"}";
//
//        mockMvc.perform(post("/register")
//                .contentType("application/json")
//                .content(userJson))
//                .andExpect(status().isOk())
//                .andDo(document("register"));
//    }
//    
//    @Test
//    public void testLogin() throws Exception {
//        mockMvc.perform(post("/login")
//                .param("username", "testuser")
//                .param("password", "password"))
//                .andExpect(status().isOk())
//                .andDo(document("login"));
//    }
//    
//    @Test
//    public void testGetAllUsers() throws Exception {
//        UserEntity user1 = new UserEntity();
//        user1.setUsername("testuser1");
//        user1.setPassword("password1");
//        user1.setNickname("nickname1");
//        user1.setRegistonDate("2024-07-03");
//        user1.setRole(Role.ROLE_USER);
//
//        UserEntity user2 = new UserEntity();
//        user2.setUsername("testuser2");
//        user2.setPassword("password2");
//        user2.setNickname("nickname2");
//        user2.setRegistonDate("2024-07-04");
//        user2.setRole(Role.ROLE_USER);
//
//        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));
//
//        mockMvc.perform(get("/"))
//                .andExpect(status().isOk())
//                .andDo(document("getAllUsers"));
//    }
//}
