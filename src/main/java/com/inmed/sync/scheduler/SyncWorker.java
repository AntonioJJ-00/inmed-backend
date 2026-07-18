package com.inmed.sync.scheduler;

import com.inmed.sync.conflict.SyncConflictResolver;
import com.inmed.sync.dto.SyncItem;
import com.inmed.sync.entity.SyncBatch;
import com.inmed.sync.entity.SyncLog;
import com.inmed.sync.entity.SyncStatus;
import com.inmed.sync.processor.SyncProcessor;
import com.inmed.sync.processor.SyncProcessorRegistry;
import com.inmed.sync.queue.SyncQueueService;
import com.inmed.sync.repository.SyncBatchRepository;
import com.inmed.sync.repository.SyncLogRepository;
import com.inmed.sync.retry.RetryService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Slf4j
@Component
@RequiredArgsConstructor
public class SyncWorker {


    private final SyncQueueService queue;

    private final SyncLogRepository logRepository;

    private final SyncBatchRepository batchRepository;

    private final SyncProcessorRegistry processorRegistry;

    private final SyncConflictResolver conflictResolver;

    private final RetryService retryService;


    @PostConstruct
    public void start(){
        Thread.startVirtualThread(() -> {
            while(true){
                try {
                    SyncLog syncLog =
                            queue.take();
                    syncLog.setStatus(
                            SyncStatus.PROCESSING
                    );
                    logRepository.save(syncLog);
                    SyncItem item =
                            new SyncItem();
                    item.setEntity(
                            syncLog.getEntityName()
                    );
                    item.setOperation(
                            syncLog.getOperation().name()
                    );
                    item.setPayload(
                            syncLog.getPayload()
                    );
                    if(conflictResolver.hasConflict(item)){
                        syncLog.setStatus(
                                SyncStatus.CONFLICT
                        );
                        syncLog.setProcessedAt(
                                LocalDateTime.now()
                        );
                        logRepository.save(syncLog);
                        continue;
                    }
                    SyncProcessor processor =
                            processorRegistry.getProcessor(
                                    syncLog.getEntityName()
                            );
                    if(processor == null){
                        throw new IllegalArgumentException(
                                "Processor not found: "
                                        + syncLog.getEntityName()
                        );
                    }
                    processor.process(item);
                    syncLog.setStatus(
                            SyncStatus.SUCCESS
                    );
                    syncLog.setProcessedAt(
                            LocalDateTime.now()
                    );
                    logRepository.save(syncLog);
                    updateBatch(
                            syncLog.getBatch()
                    );
                }
                catch(Exception ex){
                    log.error(
                            "Sync processing error",
                            ex
                    );
                }
            }
        });
    }

    private void updateBatch(
            SyncBatch batch
    ){
        batch.setProcessedOperations(
                batch.getProcessedOperations()+1
        );

        if(batch.getProcessedOperations()
                >= batch.getTotalOperations()){

            batch.setStatus(
                    SyncStatus.SUCCESS
            );

            batch.setFinishedAt(
                    LocalDateTime.now()
            );
        }

        batchRepository.save(batch);
    }
}