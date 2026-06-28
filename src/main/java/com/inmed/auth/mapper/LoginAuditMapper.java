package com.inmed.auth.mapper;

import com.inmed.auth.dto.LoginAuditResponse;
import com.inmed.auth.entity.LoginAudit;

public class LoginAuditMapper {

    private LoginAuditMapper() {
    }

    public static LoginAuditResponse toResponse(LoginAudit audit) {

        return LoginAuditResponse.builder()
                .id(audit.getId())
                .username(audit.getUsername())
                .status(audit.getStatus())
                .ipAddress(audit.getIpAddress())
                .createdAt(audit.getCreatedAt().toString())
                .build();
    }

}