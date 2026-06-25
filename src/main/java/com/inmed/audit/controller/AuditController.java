package com.inmed.audit.controller;

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
    public List<AuditLog> getLogs() {

        return auditLogRepository.findAll();
    }
}