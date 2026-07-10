package com.inmed.PosCredential.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PosLoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}