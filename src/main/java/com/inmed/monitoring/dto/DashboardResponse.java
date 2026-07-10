package com.inmed.monitoring.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DashboardResponse {

    private Integer totalPos;

    private Integer activePos;

    private Integer offlinePos;

    private Integer disabledPos;

    private List<BranchMonitoringResponse> branches;
}