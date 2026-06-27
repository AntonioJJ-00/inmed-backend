package com.inmed.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginAuditResponse {

    private Long id;
    private String username;
    private String status;
    private String ipAddress;
    private String createdAt;
}