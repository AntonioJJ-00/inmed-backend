package com.inmed.sync.dto.response;

import com.inmed.sync.entity.SyncOperation;
import com.inmed.sync.entity.SyncStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SyncLogResponse {

    private Long id;

    private String entityName;

    private SyncOperation operation;

    private SyncStatus status;

    private String payload;

    private String errorMessage;

    private Integer retryCount;

    private String deviceVersion;

    private String serverVersion;

    private LocalDateTime createdAt;

    private LocalDateTime processedAt;

}