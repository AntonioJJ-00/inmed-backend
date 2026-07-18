package com.inmed.supplier.facade;

import com.inmed.supplier.dto.request.CreateSupplierRequest;
import com.inmed.supplier.dto.request.UpdateSupplierRequest;
import com.inmed.supplier.dto.response.SupplierResponse;
import com.inmed.supplier.service.SupplierService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SupplierFacadeImpl
        implements SupplierFacade {

    private final SupplierService service;

    @Override
    public SupplierResponse create(
            CreateSupplierRequest request
    ){

        return service.create(request);

    }

    @Override
    public List<SupplierResponse> findAll(){
        return service.findAll();
    }

    @Override
    public SupplierResponse findById(
            Long id
    ){
        return service.findById(id);
    }

    @Override
    public SupplierResponse update(
            Long id,
            UpdateSupplierRequest request
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
}