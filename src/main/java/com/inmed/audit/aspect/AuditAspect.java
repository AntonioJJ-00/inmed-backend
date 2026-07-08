package com.inmed.audit.aspect;

import com.inmed.audit.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditService auditService;

    @AfterReturning("@annotation(auditable)")
    public void auditAction(JoinPoint joinPoint, com.inmed.audit.annotation.Auditable auditable) {

        String username = "SYSTEM";

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }

        String method = joinPoint.getSignature().getName();

        String target = "";

        if (joinPoint.getArgs().length > 0) {
            target = String.valueOf(joinPoint.getArgs()[0]);
        }

        auditService.save(
                username,
                auditable.action(),
                target,
                "Executed method: " + method
        );
    }
}