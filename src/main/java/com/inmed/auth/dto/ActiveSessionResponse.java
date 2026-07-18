package com.inmed.auth.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ActiveSessionResponse {

    private String sessionId;
    private Long userId;
    private String username;
    private String role;
    private String status;
    private String ipAddress;
    private String device;
    private String clientType;
    private Integer version;
    private LocalDateTime loginAt;
    private LocalDateTime lastUsedAt;
    private LocalDateTime expiresAt;
    private Long durationMinutes;
}