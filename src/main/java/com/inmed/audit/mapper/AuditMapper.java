package com.inmed.audit.mapper;

import com.inmed.audit.dto.AuditResponse;
import com.inmed.audit.entity.AuditLog;

public class AuditMapper {

    private AuditMapper() {
    }

    public static AuditResponse toResponse(AuditLog audit) {

        return AuditResponse.builder()
                .id(audit.getId())
                .adminUsername(audit.getAdminUsername())
                .action(audit.getAction())
                .targetUsername(audit.getTargetUsername())
                .reason(audit.getReason())
                .createdAt(audit.getCreatedAt())
                .build();
    }

}