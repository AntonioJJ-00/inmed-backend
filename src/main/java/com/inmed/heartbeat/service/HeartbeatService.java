package com.inmed.heartbeat.service;

import com.inmed.heartbeat.dto.HeartbeatRequest;
import com.inmed.heartbeat.dto.HeartbeatResponse;
import org.springframework.security.core.Authentication;

public interface HeartbeatService {

    HeartbeatResponse receiveHeartbeat(
            Authentication authentication,
            HeartbeatRequest request
    );
}