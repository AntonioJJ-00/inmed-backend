package com.inmed.sync.retry;

import com.inmed.sync.entity.SyncLog;

public interface RetryService {

    SyncLog scheduleRetry(
            SyncLog log
    );

}