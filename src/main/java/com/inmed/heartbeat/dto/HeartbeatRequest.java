package com.inmed.heartbeat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeartbeatRequest {

    @NotBlank(message = "App version is required")
    private String appVersion;

    @NotBlank(message = "Device serial is required")
    private String serialNumber;

    private String ipAddress;

    private String operatingSystem;

    private String hostname;

}