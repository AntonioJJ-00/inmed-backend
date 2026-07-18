package com.inmed.product.service;

import com.inmed.product.dto.request.CreateProductRequest;
import com.inmed.product.dto.request.UpdateProductRequest;
import com.inmed.product.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse create(
            CreateProductRequest request
    );

    List<ProductResponse> findAll();

    ProductResponse findById(
            Long id
    );

    ProductResponse update(
            Long id,
            UpdateProductRequest request
    );

    void deactivate(
            Long id
    );

    List<ProductResponse> findChanges(
            Integer version
    );
}