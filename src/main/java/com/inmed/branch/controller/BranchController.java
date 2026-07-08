package com.inmed.branch.controller;

import com.inmed.branch.dto.request.CreateBranchRequest;
import com.inmed.branch.dto.request.UpdateBranchRequest;
import com.inmed.branch.dto.response.BranchResponse;
import com.inmed.branch.facade.BranchFacade;
import com.inmed.common.response.ApiResponse;
import com.inmed.common.response.ResponseFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchFacade facade;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<BranchResponse> create(
            @Valid
            @RequestBody CreateBranchRequest request
    ) {

        return ResponseFactory.success(
                "Branch created successfully",
                facade.create(request)
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<BranchResponse>> findAll() {

        return ResponseFactory.success(
                "Branches retrieved successfully",
                facade.findAll()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<BranchResponse> findById(
            @PathVariable Long id
    ) {

        return ResponseFactory.success(
                "Branch retrieved successfully",
                facade.findById(id)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<BranchResponse> update(
            @PathVariable Long id,
            @RequestBody UpdateBranchRequest request
    ) {

        return ResponseFactory.success(
                "Branch updated successfully",
                facade.update(id, request)
        );
    }

    @PatchMapping("/{id}/suspend")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> suspend(
            @PathVariable Long id
    ) {

        facade.suspend(id);

        return ResponseFactory.success(
                "Branch suspended successfully"
        );
    }

}