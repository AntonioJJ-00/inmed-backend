package com.inmed.category.controller;

import com.inmed.category.dto.request.CreateCategoryRequest;
import com.inmed.category.dto.request.UpdateCategoryRequest;
import com.inmed.category.dto.response.CategoryResponse;
import com.inmed.category.facade.CategoryFacade;
import com.inmed.common.response.ApiResponse;
import com.inmed.common.response.ResponseFactory;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryFacade facade;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<CategoryResponse> create(
            @Valid
            @RequestBody CreateCategoryRequest request
    ){
        return ResponseFactory.success(
                "Category created successfully",
                facade.create(request)
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public ApiResponse<List<CategoryResponse>> findAll(){
        return ResponseFactory.success(
                "Categories retrieved successfully",
                facade.findAll()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public ApiResponse<CategoryResponse> findById(
            @PathVariable Long id
    ){
        return ResponseFactory.success(
                "Category retrieved successfully",
                facade.findById(id)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<CategoryResponse> update(
            @PathVariable Long id,
            @Valid
            @RequestBody UpdateCategoryRequest request
    ){
        return ResponseFactory.success(
                "Category updated successfully",
                facade.update(id, request)
        );
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deactivate(
            @PathVariable Long id
    ){
        facade.deactivate(id);
        return ResponseFactory.success(
                "Category deactivated successfully"
        );
    }
}