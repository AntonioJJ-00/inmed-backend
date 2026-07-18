package com.inmed.sync.repository;

import com.inmed.sync.entity.SyncLog;
import com.inmed.sync.entity.SyncStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import com.inmed.pos.entity.PosDevice;
import java.util.List;

public interface SyncLogRepository
        extends JpaRepository<SyncLog, Long> {

    List<SyncLog> findByStatus(
            SyncStatus status
    );
    List<SyncLog> findByPosDevice(
            PosDevice posDevice
    );
    List<SyncLog> findByBatchId(
            Long batchId
    );
    List<SyncLog> findByPosDeviceAndStatus(
            PosDevice posDevice,
            SyncStatus status
    );
    List<SyncLog> findByStatusAndRetryable(
            SyncStatus status,
            Boolean retryable
    );

    List<SyncLog> findByRetryableTrue();
}