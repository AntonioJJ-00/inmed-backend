package com.inmed.audit.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AuditResponse {

    private Long id;

    private String adminUsername;

    private String action;

    private String targetUsername;

    private String reason;

    private LocalDateTime createdAt;

}