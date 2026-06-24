package com.inmed.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStatusRequest {

    @NotBlank
    private String username;
}