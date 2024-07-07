package com.spring.user.request;


import com.spring.user.entity.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "User request DTO")
public class UserRequest {
    @NotNull
    @Schema(description = "Username", example = "john_does")
    private String username;

    @NotNull
    @Schema(description = "Password", example = "password123")
    private String password;

    @Schema(description = "Nickname", example = "John")
    private String nickname;

    @Schema(description = "Registration date", example = "2023-07-08")
    private String registonDate;

    @Schema(description = "Address", example = "123 Main St")
    private String address;

    @Schema(description = "User role", example = "ROLE_USER")
    private Role role;
}
