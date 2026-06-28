package com.inmed.auth.dto;

import com.inmed.user.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message="First name is required")
    private String firstName;

    @NotBlank(message="Last name is required")
    private String lastName;

    @NotBlank(message="Username is required")
    private String username;

    @NotBlank(message="Password is required")
    private String password;

    @NotNull(message="Role is required")
    private Role role;
}