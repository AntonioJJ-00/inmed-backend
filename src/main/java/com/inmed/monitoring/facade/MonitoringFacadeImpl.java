package com.inmed.monitoring.facade;

import com.inmed.monitoring.dto.DashboardResponse;
import com.inmed.monitoring.service.MonitoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonitoringFacadeImpl
        implements MonitoringFacade {

    private final MonitoringService monitoringService;

    @Override
    public DashboardResponse getDashboard() {
        return monitoringService.getDashboard();
    }
}