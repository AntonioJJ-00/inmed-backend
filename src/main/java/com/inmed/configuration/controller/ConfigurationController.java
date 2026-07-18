package com.inmed.configuration.controller;

import com.inmed.common.response.ApiResponse;
import com.inmed.common.response.ResponseFactory;
import com.inmed.configuration.dto.request.CreateConfigurationRequest;
import com.inmed.configuration.dto.request.UpdateConfigurationRequest;
import com.inmed.configuration.dto.response.ConfigurationResponse;
import com.inmed.configuration.facade.ConfigurationFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/configurations")
@RequiredArgsConstructor
public class ConfigurationController {

    private final ConfigurationFacade facade;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ConfigurationResponse> create(
            @Valid
            @RequestBody CreateConfigurationRequest request
    ) {

        return ResponseFactory.success(
                "Configuration created successfully",
                facade.create(request)
        );

    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<ConfigurationResponse>> findAll() {

        return ResponseFactory.success(
                "Configurations retrieved successfully",
                facade.findAll()
        );

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ConfigurationResponse> findById(
            @PathVariable Long id
    ) {

        return ResponseFactory.success(
                "Configuration retrieved successfully",
                facade.findById(id)
        );

    }

    @GetMapping("/scope/{scope}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<ConfigurationResponse>> findByScope(
            @PathVariable String scope
    ) {

        return ResponseFactory.success(
                "Configurations retrieved successfully",
                facade.findByScope(scope)
        );

    }

    @GetMapping("/branch/{branchId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<ConfigurationResponse>> findByBranch(
            @PathVariable Long branchId
    ) {

        return ResponseFactory.success(
                "Configurations retrieved successfully",
                facade.findByBranch(branchId)
        );

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ConfigurationResponse> update(
            @PathVariable Long id,
            @RequestBody UpdateConfigurationRequest request
    ) {

        return ResponseFactory.success(
                "Configuration updated successfully",
                facade.update(id, request)
        );

    }

    @PatchMapping("/{id}/disable")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> disable(
            @PathVariable Long id
    ) {

        facade.disable(id);

        return ResponseFactory.success(
                "Configuration disabled successfully"
        );

    }

}