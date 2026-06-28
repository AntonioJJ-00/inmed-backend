package com.inmed.audit.controller;

import com.inmed.audit.dto.AuditResponse;
import com.inmed.audit.mapper.AuditMapper;
import com.inmed.audit.repository.AuditLogRepository;
import com.inmed.common.response.ApiSuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditLogRepository auditLogRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiSuccessResponse<List<AuditResponse>>> getLogs() {

        List<AuditResponse> logs =
                auditLogRepository.findAll()
                        .stream()
                        .map(AuditMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(
                ApiSuccessResponse.<List<AuditResponse>>builder()
                        .success(true)
                        .message("Audit logs retrieved successfully")
                        .data(logs)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}