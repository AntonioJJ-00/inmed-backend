package com.inmed.monitoring.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BranchMonitoringResponse {

    private Long branchId;

    private String branchName;

    private Integer totalPos;

    private Integer activePos;

    private Integer offlinePos;

    private List<PosMonitoringResponse> pos;
}