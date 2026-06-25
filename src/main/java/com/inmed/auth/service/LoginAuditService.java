package com.inmed.auth.service;

import com.inmed.auth.entity.LoginAudit;
import com.inmed.auth.repository.LoginAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginAuditService {

    private final LoginAuditRepository repository;

    public void register(
            String username,
            String status,
            String ip
    ) {

        repository.save(
                LoginAudit.builder()
                        .username(username)
                        .status(status)
                        .ipAddress(ip)
                        .build()
        );
    }
}