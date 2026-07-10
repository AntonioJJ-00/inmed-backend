package com.inmed.monitoring.controller;

import com.inmed.common.response.ApiResponse;
import com.inmed.common.response.ResponseFactory;
import com.inmed.monitoring.dto.DashboardResponse;
import com.inmed.monitoring.facade.MonitoringFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/monitoring")
@RequiredArgsConstructor
public class MonitoringController {

    private final MonitoringFacade monitoringFacade;

    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    @GetMapping("/dashboard")
    public ApiResponse<DashboardResponse> dashboard() {

        return ResponseFactory.success(
                "Dashboard retrieved successfully",
                monitoringFacade.getDashboard()
        );
    }
}