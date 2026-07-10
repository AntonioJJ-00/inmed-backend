package com.inmed.heartbeat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HeartbeatResponse {

    private String serverTime;

    private boolean syncRequired;

    private boolean updateAvailable;

    private String latestVersion;

    private boolean maintenanceMode;

    private String message;
}