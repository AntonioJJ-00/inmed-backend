package com.inmed.pos.dto.response;

import com.inmed.pos.entity.PosStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PosResponse {

    private Long id;

    private Long branchId;

    private String branchName;

    private String code;

    private String name;

    private String serialNumber;

    private PosStatus status;

    private Boolean enabled;

    private String appVersion;

    private LocalDateTime lastHeartbeat;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}