package com.inmed.configuration.dto.request;

import com.inmed.configuration.entity.ConfigurationScope;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateConfigurationRequest {

    @NotNull
    private ConfigurationScope scope;

    private Long branchId;

    @NotBlank
    private String configKey;

    @NotBlank
    private String configValue;

    private String description;

    private Boolean enabled;

}