package com.inmed.audit.service;

import com.inmed.audit.entity.SecurityAuditLog;
import com.inmed.audit.repository.SecurityAuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SecurityAuditService {

    private final SecurityAuditLogRepository repository;

    @Transactional
    public void register(
            String username,
            String event,
            String ipAddress,
            String device,
            String sessionId,
            String metadata
    ){
        SecurityAuditLog log =
                SecurityAuditLog.builder()
                        .username(username)
                        .event(event)
                        .ipAddress(ipAddress)
                        .device(device)
                        .sessionId(sessionId)
                        .metadata(metadata)
                        .build();
        repository.save(log);
    }
}