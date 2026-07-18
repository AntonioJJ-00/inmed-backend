package com.inmed.category.service;

import com.inmed.category.dto.request.CreateCategoryRequest;
import com.inmed.category.dto.request.UpdateCategoryRequest;
import com.inmed.category.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse create(CreateCategoryRequest request);
    List<CategoryResponse> findAll();
    CategoryResponse findById(Long id);
    CategoryResponse update(
            Long id,
            UpdateCategoryRequest request
    );
    void deactivate(Long id);
}