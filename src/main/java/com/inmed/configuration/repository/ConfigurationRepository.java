package com.inmed.configuration.repository;

import com.inmed.configuration.entity.Configuration;
import com.inmed.configuration.entity.ConfigurationScope;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConfigurationRepository
        extends JpaRepository<Configuration, Long> {

    Optional<Configuration> findByScopeAndConfigKey(
            ConfigurationScope scope,
            String configKey
    );

    Optional<Configuration> findByScopeAndBranchIdAndConfigKey(
            ConfigurationScope scope,
            Long branchId,
            String configKey
    );

    boolean existsByScopeAndConfigKey(
            ConfigurationScope scope,
            String configKey
    );

    boolean existsByScopeAndBranchIdAndConfigKey(
            ConfigurationScope scope,
            Long branchId,
            String configKey
    );

    List<Configuration> findByScope(
            ConfigurationScope scope
    );

    List<Configuration> findByBranchId(
            Long branchId
    );

    List<Configuration> findByEnabledTrue();

}