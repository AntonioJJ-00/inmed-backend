package com.inmed.sync.repository;

import com.inmed.sync.entity.SyncBatch;
import com.inmed.sync.entity.SyncStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import com.inmed.pos.entity.PosDevice;

import java.util.List;
import java.util.Optional;

public interface SyncBatchRepository
        extends JpaRepository<SyncBatch,Long> {
    Optional<SyncBatch> findByBatchUuid(String batchUuid);

    List<SyncBatch> findByPosDevice(
            PosDevice posDevice
    );

    List<SyncBatch> findByStatus(
            SyncStatus status
    );
    List<SyncBatch> findByPosDeviceOrderByReceivedAtDesc(
            PosDevice posDevice
    );
}