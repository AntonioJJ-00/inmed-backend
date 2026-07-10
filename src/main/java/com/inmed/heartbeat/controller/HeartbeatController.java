package com.inmed.heartbeat.controller;

import com.inmed.common.response.ApiResponse;
import com.inmed.common.response.ResponseFactory;
import com.inmed.heartbeat.dto.HeartbeatRequest;
import com.inmed.heartbeat.dto.HeartbeatResponse;
import com.inmed.heartbeat.facade.HeartbeatFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/heartbeat")
@RequiredArgsConstructor
public class HeartbeatController {

    private final HeartbeatFacade heartbeatFacade;

    @PostMapping
    public ApiResponse<HeartbeatResponse> heartbeat(
            Authentication authentication,
            @Valid
            @RequestBody HeartbeatRequest request
    ) {

        HeartbeatResponse response =
                heartbeatFacade.receiveHeartbeat(
                        authentication,
                        request
                );


        return ResponseFactory.success(
                "Heartbeat received successfully",
                response
        );
    }
}