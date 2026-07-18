package com.inmed.inventory.service;


import com.inmed.inventory.dto.request.CreateInventoryRequest;
import com.inmed.inventory.dto.request.UpdateInventoryRequest;
import com.inmed.inventory.dto.response.InventoryResponse;

import java.util.List;


public interface InventoryService {


    InventoryResponse create(
            CreateInventoryRequest request
    );



    InventoryResponse findById(
            Long id
    );



    List<InventoryResponse> findAll();



    List<InventoryResponse> findByBranch(
            Long branchId
    );



    List<InventoryResponse> findByProduct(
            Long productId
    );



    InventoryResponse update(
            Long id,
            UpdateInventoryRequest request
    );



    void disable(
            Long id
    );


}