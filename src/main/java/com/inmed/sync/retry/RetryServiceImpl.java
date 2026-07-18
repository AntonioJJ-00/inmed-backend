package com.inmed.sync.retry;

import com.inmed.sync.entity.SyncLog;
import com.inmed.sync.entity.SyncStatus;
import com.inmed.sync.repository.SyncLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RetryServiceImpl
        implements RetryService {

    private final RetryPolicy retryPolicy;

    @Override
    public SyncLog scheduleRetry(
            SyncLog log
    ) {
        int retry = log.getRetryCount() + 1;
        log.setRetryCount(retry);
        log.setStatus(SyncStatus.FAILED);
        if (retry >= log.getMaxRetries()) {
            log.setRetryable(false);
            log.setNextRetryAt(null);
        } else {
            log.setRetryable(true);
            log.setNextRetryAt(
                    retryPolicy.nextRetry(retry)
            );
        }
        return log;
    }
}