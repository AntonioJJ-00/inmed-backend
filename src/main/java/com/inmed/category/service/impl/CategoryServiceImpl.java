package com.inmed.category.service.impl;

import com.inmed.audit.service.AuditService;
import com.inmed.category.dto.request.CreateCategoryRequest;
import com.inmed.category.dto.request.UpdateCategoryRequest;
import com.inmed.category.dto.response.CategoryResponse;
import com.inmed.category.entity.Category;
import com.inmed.category.entity.CategoryStatus;
import com.inmed.category.mapper.CategoryMapper;
import com.inmed.category.repository.CategoryRepository;
import com.inmed.category.service.CategoryService;
import com.inmed.company.entity.Company;
import com.inmed.company.repository.CompanyRepository;
import com.inmed.exception.custom.DuplicateResourceException;
import com.inmed.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl
        implements CategoryService {

    private final CategoryRepository repository;
    private final CompanyRepository companyRepository;
    private final AuditService auditService;

    @Override
    public CategoryResponse create(CreateCategoryRequest request) {
        if (repository.existsByCode(request.getCode())) {
            throw new DuplicateResourceException(
                    "Category code already exists"
            );
        }
        Company company = companyRepository
                .findTopByOrderByIdAsc()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Company not found"
                        ));
        Category category = Category.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .company(company)
                .status(CategoryStatus.ACTIVE)
                .build();
        repository.save(category);
        auditService.save(
                "SYSTEM",
                "CREATE_CATEGORY",
                category.getCode(),
                "Category created"
        );
        return CategoryMapper.toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse findById(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found"
                        ));
        return CategoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse update(
            Long id,
            UpdateCategoryRequest request
    ) {
        Category category = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found"
                        ));
        if (request.getName() != null) {
            category.setName(request.getName());
        }
        if (request.getDescription() != null) {
            category.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            category.setStatus(request.getStatus());
        }
        repository.save(category);
        auditService.save(
                "SYSTEM",
                "UPDATE_CATEGORY",
                category.getCode(),
                "Category updated"
        );
        return CategoryMapper.toResponse(category);
    }

    @Override
    public void deactivate(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found"
                        ));
        category.setStatus(CategoryStatus.INACTIVE);
        repository.save(category);
        auditService.save(
                "SYSTEM",
                "DEACTIVATE_CATEGORY",
                category.getCode(),
                "Category deactivated"
        );
    }
}