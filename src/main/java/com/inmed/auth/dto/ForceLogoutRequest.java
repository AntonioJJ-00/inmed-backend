package com.inmed.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForceLogoutRequest {

    @NotBlank
    private String username;
}