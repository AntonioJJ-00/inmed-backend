package com.inmed.audit.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditRequest {

    @NotBlank(message="Username is required")
    private String username;

    private String reason;
}