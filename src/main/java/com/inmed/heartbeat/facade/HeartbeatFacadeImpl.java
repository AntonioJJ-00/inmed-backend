package com.inmed.heartbeat.facade;

import com.inmed.heartbeat.dto.HeartbeatRequest;
import com.inmed.heartbeat.dto.HeartbeatResponse;
import com.inmed.heartbeat.service.HeartbeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HeartbeatFacadeImpl
        implements HeartbeatFacade {

    private final HeartbeatService heartbeatService;

    @Override
    public HeartbeatResponse receiveHeartbeat(
            Authentication authentication,
            HeartbeatRequest request
    ) {

        return heartbeatService.receiveHeartbeat(
                authentication,
                request
        );
    }

}