package com.inmed.product.facade;

import com.inmed.product.dto.request.CreateProductRequest;
import com.inmed.product.dto.request.UpdateProductRequest;
import com.inmed.product.dto.response.ProductResponse;

import com.inmed.product.service.ProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductFacadeImpl
        implements ProductFacade {

    private final ProductService service;

    @Override
    public ProductResponse create(
            CreateProductRequest request
    ){
        return service.create(request);
    }

    @Override
    public List<ProductResponse> findAll(){
        return service.findAll();
    }

    @Override
    public ProductResponse findById(
            Long id
    ){
        return service.findById(id);
    }

    @Override
    public ProductResponse update(
            Long id,
            UpdateProductRequest request
    ){
        return service.update(
                id,
                request
        );
    }

    @Override
    public void deactivate(
            Long id
    ){
        service.deactivate(id);
    }

    @Override
    public List<ProductResponse> findChanges(
            Integer version
    ){
        return service.findChanges(version);
    }
}