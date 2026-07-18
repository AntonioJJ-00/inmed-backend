package com.inmed.auth.audit.repository;

import com.inmed.auth.audit.entity.AuthSecurityAuditLog;
import com.inmed.auth.audit.enums.SecurityEventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecurityAuditRepository
        extends JpaRepository<AuthSecurityAuditLog, Long> {
    List<AuthSecurityAuditLog> findByUsername(
            String username
    );
    List<AuthSecurityAuditLog> findByEventType(
            SecurityEventType eventType
    );
}