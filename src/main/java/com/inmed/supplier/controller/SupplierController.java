package com.inmed.supplier.controller;

import com.inmed.common.response.ApiResponse;
import com.inmed.common.response.ResponseFactory;

import com.inmed.supplier.dto.request.CreateSupplierRequest;
import com.inmed.supplier.dto.request.UpdateSupplierRequest;
import com.inmed.supplier.dto.response.SupplierResponse;
import com.inmed.supplier.facade.SupplierFacade;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierFacade facade;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<SupplierResponse> create(
            @Valid
            @RequestBody CreateSupplierRequest request
    ){
        return ResponseFactory.success(
                "Supplier created successfully",
                facade.create(request)
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public ApiResponse<List<SupplierResponse>> findAll(){
        return ResponseFactory.success(
                "Suppliers retrieved successfully",
                facade.findAll()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public ApiResponse<SupplierResponse> findById(
            @PathVariable Long id
    ){
        return ResponseFactory.success(
                "Supplier retrieved successfully",
                facade.findById(id)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<SupplierResponse> update(
            @PathVariable Long id,
            @RequestBody UpdateSupplierRequest request
    ){
        return ResponseFactory.success(
                "Supplier updated successfully",
                facade.update(id,request)
        );
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deactivate(
            @PathVariable Long id
    ){
        facade.deactivate(id);
        return ResponseFactory.success(
                "Supplier deactivated successfully"
        );
    }
}