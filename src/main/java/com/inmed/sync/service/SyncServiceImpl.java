package com.inmed.sync.service;

import com.inmed.PosCredential.entity.PosCredential;
import com.inmed.PosCredential.repository.PosCredentialRepository;
import com.inmed.exception.custom.ResourceNotFoundException;
import com.inmed.pos.entity.PosDevice;
import com.inmed.sync.dto.SyncItem;
import com.inmed.sync.dto.request.SyncRequest;
import com.inmed.sync.dto.response.SyncBatchResponse;
import com.inmed.sync.dto.response.SyncResponse;
import com.inmed.sync.entity.SyncBatch;
import com.inmed.sync.entity.SyncLog;
import com.inmed.sync.entity.SyncOperation;
import com.inmed.sync.entity.SyncStatus;
import com.inmed.sync.mapper.SyncMapper;
import com.inmed.sync.queue.SyncQueueService;
import com.inmed.sync.repository.SyncBatchRepository;
import com.inmed.sync.repository.SyncLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SyncServiceImpl
        implements SyncService {

    private final PosCredentialRepository credentialRepository;

    private final SyncBatchRepository batchRepository;

    private final SyncLogRepository logRepository;

    private final SyncQueueService queueService;

    @Override
    public SyncResponse synchronize(
            Authentication authentication,
            SyncRequest request
    ) {
        String username = authentication.getName();
        PosCredential credential =
                credentialRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "POS credential not found"
                                )
                        );
        PosDevice pos = credential.getPosDevice();
        SyncBatch batch =
                SyncBatch.builder()
                        .batchUuid(UUID.randomUUID().toString())
                        .posDevice(pos)
                        .status(SyncStatus.RECEIVED)
                        .totalOperations(request.getItems().size())
                        .processedOperations(0)
                        .build();
        batch = batchRepository.save(batch);
        for (SyncItem item : request.getItems()) {

            SyncLog log =
                    SyncLog.builder()
                            .batch(batch)
                            .posDevice(pos)
                            .entityName(item.getEntity())
                            .operation(
                                    SyncOperation.valueOf(
                                            item.getOperation()
                                    )
                            )
                            .payload(item.getPayload())
                            .status(SyncStatus.QUEUED)
                            .build();
            logRepository.save(log);
            queueService.enqueue(log);
        }
        return SyncResponse.builder()
                .batchUuid(batch.getBatchUuid())
                .received(request.getItems().size())
                .accepted(request.getItems().size())
                .rejected(0)
                .status(batch.getStatus().name())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public SyncBatchResponse getBatch(
            String batchUuid
    ) {

        SyncBatch batch =
                batchRepository.findByBatchUuid(batchUuid)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Batch not found"
                                )
                        );

        return SyncMapper.toBatchResponse(

                batch,

                logRepository.findByBatchId(
                        batch.getId()
                )

        );

    }

    @Override
    @Transactional(readOnly = true)
    public List<SyncBatchResponse> history(
            Authentication authentication
    ) {

        PosCredential credential =
                credentialRepository.findByUsername(
                                authentication.getName()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "POS not found"
                                )
                        );

        return batchRepository
                .findByPosDeviceOrderByReceivedAtDesc(
                        credential.getPosDevice()
                )
                .stream()
                .map(batch ->

                        SyncMapper.toBatchResponse(

                                batch,

                                logRepository.findByBatchId(
                                        batch.getId()
                                )

                        )

                )
                .toList();

    }

}