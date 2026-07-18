package com.inmed.sync.facade;

import com.inmed.sync.dto.request.SyncRequest;
import com.inmed.sync.dto.response.SyncBatchResponse;
import com.inmed.sync.dto.response.SyncResponse;
import com.inmed.sync.service.SyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SyncFacadeImpl
        implements SyncFacade {

    private final SyncService syncService;

    @Override
    public SyncResponse synchronize(
            Authentication authentication,
            SyncRequest request
    ) {
        return syncService.synchronize(
                authentication,
                request
        );
    }
    @Override
    public SyncBatchResponse getBatch(
            String batchUuid
    ) {
        return syncService.getBatch(
                batchUuid
        );
    }

    @Override
    public List<SyncBatchResponse> history(
            Authentication authentication
    ) {
        return syncService.history(
                authentication
        );
    }
}