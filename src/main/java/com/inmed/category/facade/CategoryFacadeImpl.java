package com.inmed.category.facade;

import com.inmed.category.dto.request.CreateCategoryRequest;
import com.inmed.category.dto.request.UpdateCategoryRequest;
import com.inmed.category.dto.response.CategoryResponse;
import com.inmed.category.service.CategoryService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryFacadeImpl
        implements CategoryFacade {

    private final CategoryService service;

    @Override
    public CategoryResponse create(
            CreateCategoryRequest request
    ) {
        return service.create(request);
    }

    @Override
    public List<CategoryResponse> findAll() {
        return service.findAll();
    }

    @Override
    public CategoryResponse findById(
            Long id
    ) {
        return service.findById(id);
    }

    @Override
    public CategoryResponse update(
            Long id,
            UpdateCategoryRequest request
    ) {
        return service.update(id, request);
    }

    @Override
    public void deactivate(
            Long id
    ) {
        service.deactivate(id);
    }
}