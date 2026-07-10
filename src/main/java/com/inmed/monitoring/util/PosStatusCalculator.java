package com.inmed.monitoring.util;

import com.inmed.pos.entity.PosDevice;
import com.inmed.pos.entity.PosStatus;

import java.time.Duration;
import java.time.LocalDateTime;

public final class PosStatusCalculator {

    private static final long OFFLINE_SECONDS = 180;

    private PosStatusCalculator() {
    }

    public static PosStatus calculate(PosDevice pos) {

        if (!Boolean.TRUE.equals(pos.getEnabled())) {
            return PosStatus.INACTIVE;
        }

        if (pos.getStatus() == PosStatus.MAINTENANCE) {
            return PosStatus.MAINTENANCE;
        }

        if (pos.getLastHeartbeat() == null) {
            return PosStatus.INACTIVE;
        }

        long seconds = Duration
                .between(pos.getLastHeartbeat(), LocalDateTime.now())
                .getSeconds();

        if (seconds > OFFLINE_SECONDS) {
            return PosStatus.INACTIVE;
        }

        return PosStatus.ACTIVE;
    }

}