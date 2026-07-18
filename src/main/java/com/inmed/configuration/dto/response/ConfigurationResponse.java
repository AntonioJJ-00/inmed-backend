package com.inmed.configuration.dto.response;

import com.inmed.configuration.entity.ConfigurationScope;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ConfigurationResponse {

    private Long id;

    private Long companyId;

    private Long branchId;

    private ConfigurationScope scope;

    private String configKey;

    private String configValue;

    private String description;

    private Boolean enabled;

    private Long version;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}