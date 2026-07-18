package com.inmed.sync.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SyncResponse {

    private String batchUuid;

    private Integer received;

    private Integer accepted;

    private Integer rejected;

    private String status;
}