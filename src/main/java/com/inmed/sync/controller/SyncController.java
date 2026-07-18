package com.inmed.sync.controller;

import com.inmed.common.response.ApiResponse;
import com.inmed.common.response.ResponseFactory;
import com.inmed.sync.dto.request.SyncRequest;
import com.inmed.sync.dto.response.SyncBatchResponse;
import com.inmed.sync.dto.response.SyncResponse;
import com.inmed.sync.facade.SyncFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sync")
@RequiredArgsConstructor
public class SyncController {

    private final SyncFacade syncFacade;

    @PostMapping
    public ApiResponse<SyncResponse> synchronize(
            Authentication authentication,
            @Valid
            @RequestBody
            SyncRequest request
    ) {
        return ResponseFactory.success(
                "Synchronization accepted",
                syncFacade.synchronize(
                        authentication,
                        request
                )
        );
    }

    @GetMapping("/{batchUuid}")
    public ApiResponse<SyncBatchResponse> getBatch(

            @PathVariable
            String batchUuid

    ) {

        return ResponseFactory.success(

                "Synchronization retrieved successfully",

                syncFacade.getBatch(
                        batchUuid
                )

        );

    }

}