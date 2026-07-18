package com.inmed.sync.util;

import com.inmed.sync.entity.SyncBatch;
import com.inmed.sync.entity.SyncLog;
import com.inmed.sync.entity.SyncStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SyncStatusCalculator {

    public void calculateStatus(
            SyncBatch batch,
            List<SyncLog> logs
    ) {

        long success =
                logs.stream()
                        .filter(l -> l.getStatus() == SyncStatus.SUCCESS)
                        .count();

        long failed =
                logs.stream()
                        .filter(l -> l.getStatus() == SyncStatus.FAILED)
                        .count();

        long conflict =
                logs.stream()
                        .filter(l -> l.getStatus() == SyncStatus.CONFLICT)
                        .count();

        long processing =
                logs.stream()
                        .filter(l -> l.getStatus() == SyncStatus.PROCESSING)
                        .count();

        if (processing > 0) {

            batch.setStatus(
                    SyncStatus.PROCESSING
            );

            return;
        }

        if (conflict > 0) {

            batch.setStatus(
                    SyncStatus.CONFLICT
            );

        }

        else if (failed > 0) {

            batch.setStatus(
                    SyncStatus.FAILED
            );

        }

        else {

            batch.setStatus(
                    SyncStatus.SUCCESS
            );

        }

        batch.setProcessedOperations(
                (int) success
        );

        batch.setFinishedAt(
                LocalDateTime.now()
        );

    }

}