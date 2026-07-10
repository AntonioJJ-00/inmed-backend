package com.inmed.pos.mapper;

import com.inmed.pos.dto.response.PosResponse;
import com.inmed.pos.entity.PosDevice;

import java.util.List;

public class PosMapper {

    private PosMapper() {
    }

    public static PosResponse toResponse(PosDevice pos) {

        if (pos == null) {
            return null;
        }

        return PosResponse.builder()
                .id(pos.getId())
                .branchId(pos.getBranch().getId())
                .branchName(pos.getBranch().getName())
                .code(pos.getCode())
                .name(pos.getName())
                .serialNumber(pos.getSerialNumber())
                .status(pos.getStatus())
                .enabled(pos.getEnabled())
                .appVersion(pos.getAppVersion())
                .lastIp(pos.getLastIp())
                .hostname(pos.getHostname())
                .operatingSystem(pos.getOperatingSystem())
                .lastHeartbeat(pos.getLastHeartbeat())
                .createdAt(pos.getCreatedAt())
                .updatedAt(pos.getUpdatedAt())
                .build();

    }

    public static List<PosResponse> toResponseList(List<PosDevice> list) {

        return list.stream()
                .map(PosMapper::toResponse)
                .toList();

    }

}