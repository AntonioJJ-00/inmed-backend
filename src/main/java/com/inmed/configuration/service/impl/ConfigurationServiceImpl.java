package com.inmed.configuration.service.impl;

import com.inmed.audit.service.AuditService;
import com.inmed.branch.entity.Branch;
import com.inmed.branch.repository.BranchRepository;
import com.inmed.company.entity.Company;
import com.inmed.company.repository.CompanyRepository;
import com.inmed.configuration.dto.request.CreateConfigurationRequest;
import com.inmed.configuration.dto.request.UpdateConfigurationRequest;
import com.inmed.configuration.dto.response.ConfigurationResponse;
import com.inmed.configuration.entity.Configuration;
import com.inmed.configuration.entity.ConfigurationScope;
import com.inmed.configuration.mapper.ConfigurationMapper;
import com.inmed.configuration.repository.ConfigurationRepository;
import com.inmed.configuration.service.ConfigurationService;
import com.inmed.exception.custom.DuplicateResourceException;
import com.inmed.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ConfigurationServiceImpl
        implements ConfigurationService {

    private final ConfigurationRepository repository;

    private final CompanyRepository companyRepository;

    private final BranchRepository branchRepository;

    private final AuditService auditService;

    @Override
    public ConfigurationResponse create(
            CreateConfigurationRequest request
    ) {

        Company company = companyRepository
                .findTopByOrderByIdAsc()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Company not found"
                        ));

        Branch branch = null;

        if (request.getBranchId() != null) {

            branch = branchRepository
                    .findById(request.getBranchId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Branch not found"
                            ));
        }

        if (request.getScope() == ConfigurationScope.BRANCH) {

            if (repository.existsByScopeAndBranchIdAndConfigKey(
                    request.getScope(),
                    request.getBranchId(),
                    request.getConfigKey()
            )) {

                throw new DuplicateResourceException(
                        "Configuration already exists"
                );
            }

        } else {

            if (repository.existsByScopeAndConfigKey(
                    request.getScope(),
                    request.getConfigKey()
            )) {

                throw new DuplicateResourceException(
                        "Configuration already exists"
                );
            }

        }

        Configuration configuration =
                Configuration.builder()

                        .company(company)

                        .branch(branch)

                        .scope(request.getScope())

                        .configKey(request.getConfigKey())

                        .configValue(request.getConfigValue())

                        .description(request.getDescription())

                        .enabled(
                                request.getEnabled() == null
                                        ? true
                                        : request.getEnabled()
                        )

                        .build();

        repository.save(configuration);

        auditService.save(
                "SYSTEM",
                "CREATE_CONFIGURATION",
                configuration.getConfigKey(),
                "Configuration created"
        );

        return ConfigurationMapper.toResponse(
                configuration
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConfigurationResponse> findAll() {

        return repository.findAll()

                .stream()

                .map(ConfigurationMapper::toResponse)

                .toList();

    }

    @Override
    @Transactional(readOnly = true)
    public ConfigurationResponse findById(Long id) {

        Configuration configuration =
                repository.findById(id)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Configuration not found"
                                ));

        return ConfigurationMapper.toResponse(
                configuration
        );

    }

    @Override
    @Transactional(readOnly = true)
    public List<ConfigurationResponse> findByScope(
            String scope
    ) {

        return repository.findByScope(
                        ConfigurationScope.valueOf(
                                scope.toUpperCase()
                        )
                )

                .stream()

                .map(ConfigurationMapper::toResponse)

                .toList();

    }

    @Override
    @Transactional(readOnly = true)
    public List<ConfigurationResponse> findByBranch(
            Long branchId
    ) {

        return repository.findByBranchId(branchId)

                .stream()

                .map(ConfigurationMapper::toResponse)

                .toList();

    }

    @Override
    public ConfigurationResponse update(
            Long id,
            UpdateConfigurationRequest request
    ) {

        Configuration configuration =
                repository.findById(id)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Configuration not found"
                                ));

        if (request.getConfigValue() != null) {

            configuration.setConfigValue(
                    request.getConfigValue()
            );

        }

        if (request.getDescription() != null) {

            configuration.setDescription(
                    request.getDescription()
            );

        }

        if (request.getEnabled() != null) {

            configuration.setEnabled(
                    request.getEnabled()
            );

        }

        repository.save(configuration);

        auditService.save(
                "SYSTEM",
                "UPDATE_CONFIGURATION",
                configuration.getConfigKey(),
                "Configuration updated"
        );

        return ConfigurationMapper.toResponse(
                configuration
        );

    }

    @Override
    public void disable(Long id) {

        Configuration configuration =
                repository.findById(id)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Configuration not found"
                                ));

        configuration.setEnabled(false);

        repository.save(configuration);

        auditService.save(
                "SYSTEM",
                "DISABLE_CONFIGURATION",
                configuration.getConfigKey(),
                "Configuration disabled"
        );

    }

}