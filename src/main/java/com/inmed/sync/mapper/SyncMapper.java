package com.inmed.sync.mapper;

import com.inmed.sync.dto.response.SyncBatchResponse;
import com.inmed.sync.dto.response.SyncLogResponse;
import com.inmed.sync.entity.SyncBatch;
import com.inmed.sync.entity.SyncLog;

import java.util.List;

public final class SyncMapper {

    private SyncMapper() {
    }

    public static SyncLogResponse toLogResponse(
            SyncLog log
    ) {

        if (log == null) {
            return null;
        }

        return SyncLogResponse.builder()
                .id(log.getId())
                .entityName(log.getEntityName())
                .operation(log.getOperation())
                .status(log.getStatus())
                .payload(log.getPayload())
                .errorMessage(log.getErrorMessage())
                .retryCount(log.getRetryCount())
                .deviceVersion(log.getDeviceVersion())
                .serverVersion(log.getServerVersion())
                .createdAt(log.getCreatedAt())
                .processedAt(log.getProcessedAt())
                .build();
    }

    public static List<SyncLogResponse> toLogResponseList(
            List<SyncLog> logs
    ) {
        return logs.stream()
                .map(SyncMapper::toLogResponse)
                .toList();
    }

    public static SyncBatchResponse toBatchResponse(
            SyncBatch batch,
            List<SyncLog> logs
    ) {

        if (batch == null) {
            return null;
        }

        return SyncBatchResponse.builder()
                .id(batch.getId())
                .batchUuid(batch.getBatchUuid())
                .posId(batch.getPosDevice().getId())
                .posCode(batch.getPosDevice().getCode())
                .status(batch.getStatus())
                .totalOperations(batch.getTotalOperations())
                .processedOperations(batch.getProcessedOperations())
                .successOperations(batch.getSuccessOperations())
                .failedOperations(batch.getFailedOperations())
                .conflictOperations(batch.getConflictOperations())
                .receivedAt(batch.getReceivedAt())
                .finishedAt(batch.getFinishedAt())
                .logs(toLogResponseList(logs))
                .build();

    }

}