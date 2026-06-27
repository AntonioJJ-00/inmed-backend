package com.inmed.audit.controller;

import com.inmed.audit.dto.AuditLogResponse;
import com.inmed.audit.entity.AuditLog;
import com.inmed.audit.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditLogRepository auditLogRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<AuditLogResponse> getLogs() {

        return auditLogRepository.findAll()
                .stream()
                .map(log -> AuditLogResponse.builder()
                        .id(log.getId())
                        .adminUsername(log.getAdminUsername())
                        .action(log.getAction())
                        .targetUsername(log.getTargetUsername())
                        .reason(log.getReason())
                        .createdAt(log.getCreatedAt())
                        .build()
                )
                .toList();
    }
}