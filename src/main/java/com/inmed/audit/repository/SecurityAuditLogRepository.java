package com.inmed.audit.repository;

import com.inmed.audit.entity.SecurityAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecurityAuditLogRepository
        extends JpaRepository<SecurityAuditLog, Long> {
    List<SecurityAuditLog> findByUsernameOrderByCreatedAtDesc(
            String username
    );
    List<SecurityAuditLog> findByEventOrderByCreatedAtDesc(
            String event
    );
}