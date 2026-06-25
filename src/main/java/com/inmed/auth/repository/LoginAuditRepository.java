package com.inmed.auth.repository;

import com.inmed.auth.entity.LoginAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginAuditRepository
        extends JpaRepository<LoginAudit, Long> {
}