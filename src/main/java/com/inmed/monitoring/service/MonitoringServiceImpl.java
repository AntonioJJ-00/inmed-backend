package com.inmed.monitoring.service;

import com.inmed.monitoring.dto.DashboardResponse;
import com.inmed.monitoring.dto.BranchMonitoringResponse;
import com.inmed.pos.repository.PosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitoringServiceImpl
        implements MonitoringService {

    private final PosRepository posRepository;

    @Override
    public DashboardResponse getDashboard() {

        return DashboardResponse.builder()

                .totalPos(
                        (int) posRepository.count()
                )

                .activePos(0)

                .offlinePos(0)

                .disabledPos(0)

                .branches(List.of())

                .build();
    }

}