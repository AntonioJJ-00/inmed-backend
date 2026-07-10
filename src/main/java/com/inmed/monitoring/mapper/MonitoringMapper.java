package com.inmed.monitoring.mapper;

import com.inmed.monitoring.dto.PosMonitoringResponse;
import com.inmed.monitoring.util.PosStatusCalculator;
import com.inmed.pos.entity.PosDevice;

public final class MonitoringMapper {

    private MonitoringMapper() {
    }

    public static PosMonitoringResponse toResponse(PosDevice pos) {

        return PosMonitoringResponse.builder()
                .id(pos.getId())
                .code(pos.getCode())
                .serialNumber(pos.getSerialNumber())
                .status(
                        PosStatusCalculator
                                .calculate(pos)
                                .name()
                )
                .appVersion(pos.getAppVersion())
                .lastHeartbeat(
                        pos.getLastHeartbeat() == null
                                ? null
                                : pos.getLastHeartbeat().toString()
                )
                .lastIp(pos.getLastIp())
                .hostname(pos.getHostname())
                .operatingSystem(pos.getOperatingSystem())
                .enabled(pos.getEnabled())
                .build();
    }

}