package com.inmed.configuration.facade;

import com.inmed.configuration.dto.request.CreateConfigurationRequest;
import com.inmed.configuration.dto.request.UpdateConfigurationRequest;
import com.inmed.configuration.dto.response.ConfigurationResponse;

import java.util.List;

public interface ConfigurationFacade {

    ConfigurationResponse create(
            CreateConfigurationRequest request
    );

    List<ConfigurationResponse> findAll();

    ConfigurationResponse findById(
            Long id
    );

    List<ConfigurationResponse> findByScope(
            String scope
    );

    List<ConfigurationResponse> findByBranch(
            Long branchId
    );

    ConfigurationResponse update(
            Long id,
            UpdateConfigurationRequest request
    );

    void disable(
            Long id
    );

}