package com.inmed.heartbeat.facade;

import com.inmed.heartbeat.dto.HeartbeatRequest;
import com.inmed.heartbeat.dto.HeartbeatResponse;
import org.springframework.security.core.Authentication;

public interface HeartbeatFacade {

    HeartbeatResponse receiveHeartbeat(
            Authentication authentication,
            HeartbeatRequest request
    );
}