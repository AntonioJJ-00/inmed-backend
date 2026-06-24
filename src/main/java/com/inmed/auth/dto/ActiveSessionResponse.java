package com.inmed.auth.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ActiveSessionResponse {

    private String username;

    private String role;

    private LocalDateTime expiresAt;
}