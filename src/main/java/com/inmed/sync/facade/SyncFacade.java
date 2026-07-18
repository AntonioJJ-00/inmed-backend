package com.inmed.sync.facade;

import com.inmed.sync.dto.request.SyncRequest;
import com.inmed.sync.dto.response.SyncBatchResponse;
import com.inmed.sync.dto.response.SyncResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface SyncFacade {

    SyncResponse synchronize(
            Authentication authentication,
            SyncRequest request
    );
    SyncBatchResponse getBatch(
            String batchUuid
    );

    List<SyncBatchResponse> history(
            Authentication authentication
    );
}