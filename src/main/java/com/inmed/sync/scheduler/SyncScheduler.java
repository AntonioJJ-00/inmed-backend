package com.inmed.sync.scheduler;

import com.inmed.sync.entity.SyncLog;
import com.inmed.sync.entity.SyncStatus;
import com.inmed.sync.queue.SyncQueueService;
import com.inmed.sync.repository.SyncLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SyncScheduler {

    private final SyncLogRepository repository;

    private final SyncQueueService queueService;

    @Scheduled(fixedDelay = 30000)
    public void retryPendingEvents() {
        List<SyncLog> logs =
                repository.findByRetryableTrue();
        for (SyncLog syncLog : logs) {
            if (syncLog.getStatus() != SyncStatus.FAILED) {
                continue;
            }
            if (syncLog.getNextRetryAt() == null) {
                continue;
            }
            if (syncLog.getNextRetryAt().isAfter(LocalDateTime.now())) {
                continue;
            }
            syncLog.setStatus(SyncStatus.QUEUED);
            repository.save(syncLog);
            queueService.enqueue(syncLog);
            log.info(
                    "Retry queued for SyncLog {}",
                    syncLog.getId()
            );
        }
    }
}