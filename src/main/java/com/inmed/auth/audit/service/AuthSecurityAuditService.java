package com.inmed.auth.audit.service;

import com.inmed.auth.audit.entity.AuthSecurityAuditLog;
import com.inmed.auth.audit.enums.SecurityEventType;
import com.inmed.auth.audit.repository.SecurityAuditRepository;
import com.inmed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthSecurityAuditService {

    private final SecurityAuditRepository repository;

    @Transactional
    public void log(
            SecurityEventType eventType,
            User user,
            String ipAddress,
            String device,
            String sessionId,
            String metadata
    ){
        AuthSecurityAuditLog audit =
                AuthSecurityAuditLog.builder()
                        .user(user)
                        .username(
                                user != null
                                        ?
                                        user.getUsername()
                                        :
                                        "UNKNOWN"
                        )
                        .eventType(eventType)
                        .ipAddress(ipAddress)
                        .device(device)
                        .sessionId(sessionId)
                        .metadata(metadata)
                        .createdAt(
                                LocalDateTime.now()
                        )
                        .build();
        repository.save(audit);
    }

    @Transactional
    public void logAnonymous(
            SecurityEventType eventType,
            String username,
            String ipAddress,
            String device,
            String metadata
    ){
        AuthSecurityAuditLog audit =
                AuthSecurityAuditLog.builder()
                        .username(username)
                        .eventType(eventType)
                        .ipAddress(ipAddress)
                        .device(device)
                        .metadata(metadata)
                        .createdAt(
                                LocalDateTime.now()
                        )
                        .build();
        repository.save(audit);
    }
}