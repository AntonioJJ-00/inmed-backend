package com.inmed.configuration.facade;

import com.inmed.configuration.dto.request.CreateConfigurationRequest;
import com.inmed.configuration.dto.request.UpdateConfigurationRequest;
import com.inmed.configuration.dto.response.ConfigurationResponse;
import com.inmed.configuration.service.ConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConfigurationFacadeImpl
        implements ConfigurationFacade {

    private final ConfigurationService service;

    @Override
    public ConfigurationResponse create(
            CreateConfigurationRequest request
    ) {
        return service.create(request);
    }

    @Override
    public List<ConfigurationResponse> findAll() {
        return service.findAll();
    }

    @Override
    public ConfigurationResponse findById(
            Long id
    ) {
        return service.findById(id);
    }

    @Override
    public List<ConfigurationResponse> findByScope(
            String scope
    ) {
        return service.findByScope(scope);
    }

    @Override
    public List<ConfigurationResponse> findByBranch(
            Long branchId
    ) {
        return service.findByBranch(branchId);
    }

    @Override
    public ConfigurationResponse update(
            Long id,
            UpdateConfigurationRequest request
    ) {
        return service.update(id, request);
    }

    @Override
    public void disable(Long id) {
        service.disable(id);
    }

}