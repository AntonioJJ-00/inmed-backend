package com.inmed.monitoring.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PosMonitoringResponse {

    private Long id;

    private String code;

    private String serialNumber;

    private String status;

    private String appVersion;

    private String lastHeartbeat;

    private String lastIp;

    private String hostname;

    private String operatingSystem;

    private boolean enabled;
}