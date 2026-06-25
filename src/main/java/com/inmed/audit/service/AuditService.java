package com.inmed.audit.service;

import com.inmed.audit.entity.AuditLog;
import com.inmed.audit.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public void save(
            String admin,
            String action,
            String target,
            String reason
    ) {

        AuditLog log =
                AuditLog.builder()
                        .adminUsername(admin)
                        .action(action)
                        .targetUsername(target)
                        .reason(reason)
                        .build();

        auditLogRepository.save(log);
    }
}