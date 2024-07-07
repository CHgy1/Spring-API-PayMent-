package com.spring.user.response;

import java.util.List;

import com.spring.payment.request.ProductRequest;
import com.spring.user.entity.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "User response DTO")
public class UserResponse {
    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "Username", example = "john_does")
    private String username;

    @Schema(description = "Nickname", example = "John")
    private String nickname;

    @Schema(description = "Registration date", example = "2023-07-08")
    private String registonDate;

    @Schema(description = "Address", example = "123 Main St")
    private String address;

    @Schema(description = "User role", example = "ROLE_USER")
    private Role role;

    @Schema(description = "List of products")
    private List<ProductRequest> products;
}
