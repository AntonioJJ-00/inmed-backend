package com.inmed.product.controller;

import com.inmed.common.response.ApiResponse;
import com.inmed.common.response.ResponseFactory;

import com.inmed.product.dto.request.CreateProductRequest;
import com.inmed.product.dto.request.UpdateProductRequest;
import com.inmed.product.dto.response.ProductResponse;

import com.inmed.product.facade.ProductFacade;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductFacade facade;
    /**
     * Crear producto maestro
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ProductResponse> create(
            @Valid
            @RequestBody CreateProductRequest request
    ){
        return ResponseFactory.success(
                "Product created successfully",
                facade.create(request)
        );
    }
    /**
     * Obtener catálogo completo
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public ApiResponse<List<ProductResponse>> findAll(){
        return ResponseFactory.success(
                "Products retrieved successfully",
                facade.findAll()
        );
    }
    /**
     * Obtener producto por ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public ApiResponse<ProductResponse> findById(
            @PathVariable Long id
    ){
        return ResponseFactory.success(
                "Product retrieved successfully",
                facade.findById(id)
        );
    }
    /**
     * Actualizar producto
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ProductResponse> update(
            @PathVariable Long id,
            @Valid
            @RequestBody UpdateProductRequest request
    ){
        return ResponseFactory.success(
                "Product updated successfully",
                facade.update(
                        id,
                        request
                )
        );
    }
    /**
     * Desactivar producto
     */
    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deactivate(
            @PathVariable Long id
    ){
        facade.deactivate(id);
        return ResponseFactory.success(
                "Product deactivated successfully"
        );
    }
    /**
     * Endpoint utilizado por Sync Engine
     *
     * POS LOCAL consulta:
     *
     * /sync/10
     *
     * devuelve cambios posteriores
     *
     */
    @GetMapping("/sync/{version}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<ProductResponse>> sync(
            @PathVariable Integer version
    ){
        return ResponseFactory.success(
                "Product changes retrieved successfully",
                facade.findChanges(version)
        );
    }
}