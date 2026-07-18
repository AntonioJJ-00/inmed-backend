package com.inmed.configuration.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateConfigurationRequest {

    private String configValue;

    private String description;

    private Boolean enabled;

}