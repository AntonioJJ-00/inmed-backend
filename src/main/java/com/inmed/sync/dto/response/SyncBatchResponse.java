package com.inmed.sync.dto.response;

import com.inmed.sync.entity.SyncStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class SyncBatchResponse {

    private Long id;

    private String batchUuid;

    private Long posId;

    private String posCode;

    private SyncStatus status;

    private Integer totalOperations;

    private Integer processedOperations;

    private Integer successOperations;

    private Integer failedOperations;

    private Integer conflictOperations;

    private LocalDateTime receivedAt;

    private LocalDateTime finishedAt;

    private List<SyncLogResponse> logs;

}