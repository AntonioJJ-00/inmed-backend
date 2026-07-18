package com.inmed.supplier.service;

import com.inmed.supplier.dto.request.CreateSupplierRequest;
import com.inmed.supplier.dto.request.UpdateSupplierRequest;
import com.inmed.supplier.dto.response.SupplierResponse;

import java.util.List;

public interface SupplierService {

    SupplierResponse create(
            CreateSupplierRequest request
    );

    List<SupplierResponse> findAll();

    SupplierResponse findById(
            Long id
    );

    SupplierResponse update(
            Long id,
            UpdateSupplierRequest request
    );

    void deactivate(
            Long id
    );
}