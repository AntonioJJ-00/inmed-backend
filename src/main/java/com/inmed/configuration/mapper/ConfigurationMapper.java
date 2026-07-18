package com.inmed.configuration.mapper;

import com.inmed.configuration.dto.response.ConfigurationResponse;
import com.inmed.configuration.entity.Configuration;

public final class ConfigurationMapper {

    private ConfigurationMapper() {
    }

    public static ConfigurationResponse toResponse(
            Configuration configuration
    ) {

        return ConfigurationResponse.builder()

                .id(configuration.getId())

                .companyId(
                        configuration.getCompany().getId()
                )

                .branchId(
                        configuration.getBranch() == null
                                ? null
                                : configuration.getBranch().getId()
                )

                .scope(
                        configuration.getScope()
                )

                .configKey(
                        configuration.getConfigKey()
                )

                .configValue(
                        configuration.getConfigValue()
                )
                .description(
                        configuration.getDescription()
                )
                .enabled(
                        configuration.getEnabled()
                )
                .version(
                        configuration.getVersion()
                )
                .createdAt(
                        configuration.getCreatedAt()
                )
                .updatedAt(
                        configuration.getUpdatedAt()
                )
                .build();
    }
}