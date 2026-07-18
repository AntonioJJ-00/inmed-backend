package com.inmed.inventory.controller;


import com.inmed.common.response.ApiResponse;
import com.inmed.common.response.ResponseFactory;

import com.inmed.inventory.dto.request.CreateInventoryRequest;
import com.inmed.inventory.dto.request.UpdateInventoryRequest;
import com.inmed.inventory.dto.response.InventoryResponse;

import com.inmed.inventory.facade.InventoryFacade;


import jakarta.validation.Valid;


import lombok.RequiredArgsConstructor;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {



    private final InventoryFacade facade;





    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<InventoryResponse> create(
            @Valid
            @RequestBody CreateInventoryRequest request
    ){

        return ResponseFactory.success(
                "Inventory created successfully",
                facade.create(request)
        );

    }







    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public ApiResponse<List<InventoryResponse>> findAll(){

        return ResponseFactory.success(
                "Inventory retrieved successfully",
                facade.findAll()
        );

    }







    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public ApiResponse<InventoryResponse> findById(
            @PathVariable Long id
    ){

        return ResponseFactory.success(
                "Inventory retrieved successfully",
                facade.findById(id)
        );

    }







    @GetMapping("/branch/{branchId}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public ApiResponse<List<InventoryResponse>> findByBranch(
            @PathVariable Long branchId
    ){

        return ResponseFactory.success(
                "Branch inventory retrieved successfully",
                facade.findByBranch(branchId)
        );

    }







    @GetMapping("/product/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public ApiResponse<List<InventoryResponse>> findByProduct(
            @PathVariable Long productId
    ){

        return ResponseFactory.success(
                "Product inventory retrieved successfully",
                facade.findByProduct(productId)
        );

    }







    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<InventoryResponse> update(
            @PathVariable Long id,
            @Valid
            @RequestBody UpdateInventoryRequest request
    ){

        return ResponseFactory.success(
                "Inventory updated successfully",
                facade.update(
                        id,
                        request
                )
        );

    }







    @PatchMapping("/{id}/disable")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> disable(
            @PathVariable Long id
    ){

        facade.disable(id);


        return ResponseFactory.success(
                "Inventory disabled successfully"
        );

    }


}