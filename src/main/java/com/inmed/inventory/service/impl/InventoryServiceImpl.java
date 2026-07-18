package com.inmed.inventory.service.impl;


import com.inmed.audit.service.AuditService;
import com.inmed.branch.entity.Branch;
import com.inmed.branch.repository.BranchRepository;
import com.inmed.exception.custom.DuplicateResourceException;
import com.inmed.exception.custom.ResourceNotFoundException;
import com.inmed.inventory.dto.request.CreateInventoryRequest;
import com.inmed.inventory.dto.request.UpdateInventoryRequest;
import com.inmed.inventory.dto.response.InventoryResponse;
import com.inmed.inventory.entity.Inventory;
import com.inmed.inventory.mapper.InventoryMapper;
import com.inmed.inventory.repository.InventoryRepository;
import com.inmed.inventory.service.InventoryService;
import com.inmed.product.entity.Product;
import com.inmed.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl
        implements InventoryService {



    private final InventoryRepository repository;


    private final BranchRepository branchRepository;


    private final ProductRepository productRepository;


    private final AuditService auditService;




    @Override
    public InventoryResponse create(
            CreateInventoryRequest request
    ){


        boolean exists =
                repository.existsByBranchIdAndProductId(
                        request.getBranchId(),
                        request.getProductId()
                );


        if(exists){

            throw new DuplicateResourceException(
                    "Inventory already exists for this product and branch"
            );

        }



        Branch branch =
                branchRepository.findById(
                                request.getBranchId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Branch not found"
                                )
                        );



        Product product =
                productRepository.findById(
                                request.getProductId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found"
                                )
                        );



        Inventory inventory =
                Inventory.builder()

                        .branch(branch)

                        .product(product)

                        .stock(
                                request.getStock()
                        )

                        .reservedStock(
                                request.getReservedStock()
                        )

                        .minimumStock(
                                request.getMinimumStock()
                        )

                        .maximumStock(
                                request.getMaximumStock()
                        )

                        .lastMovement(
                                LocalDateTime.now()
                        )

                        .lastSync(
                                null
                        )

                        .version(
                                1L
                        )

                        .enabled(true)

                        .build();



        repository.save(inventory);



        auditService.save(
                "SYSTEM",
                "CREATE_INVENTORY",
                inventory.getId().toString(),
                "Inventory created"
        );



        return InventoryMapper.toResponse(
                inventory
        );

    }







    @Override
    @Transactional(readOnly = true)
    public InventoryResponse findById(
            Long id
    ){

        Inventory inventory =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Inventory not found"
                                )
                        );


        return InventoryMapper.toResponse(
                inventory
        );

    }







    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> findAll(){


        return repository.findAll()
                .stream()
                .map(
                        InventoryMapper::toResponse
                )
                .toList();

    }







    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> findByBranch(
            Long branchId
    ){


        return repository
                .findByBranchId(branchId)
                .stream()
                .map(
                        InventoryMapper::toResponse
                )
                .toList();

    }







    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> findByProduct(
            Long productId
    ){


        return repository
                .findByProductId(productId)
                .stream()
                .map(
                        InventoryMapper::toResponse
                )
                .toList();

    }







    @Override
    public InventoryResponse update(
            Long id,
            UpdateInventoryRequest request
    ){


        Inventory inventory =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Inventory not found"
                                )
                        );



        if(request.getStock()!=null){

            inventory.setStock(
                    request.getStock()
            );

        }



        if(request.getReservedStock()!=null){

            inventory.setReservedStock(
                    request.getReservedStock()
            );

        }



        if(request.getMinimumStock()!=null){

            inventory.setMinimumStock(
                    request.getMinimumStock()
            );

        }



        if(request.getMaximumStock()!=null){

            inventory.setMaximumStock(
                    request.getMaximumStock()
            );

        }



        if(request.getEnabled()!=null){

            inventory.setEnabled(
                    request.getEnabled()
            );

        }



        inventory.setLastMovement(
                LocalDateTime.now()
        );


        inventory.setVersion(
                inventory.getVersion()+1
        );



        repository.save(
                inventory
        );



        auditService.save(
                "SYSTEM",
                "UPDATE_INVENTORY",
                inventory.getId().toString(),
                "Inventory updated"
        );



        return InventoryMapper.toResponse(
                inventory
        );

    }







    @Override
    public void disable(
            Long id
    ){


        Inventory inventory =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Inventory not found"
                                )
                        );



        inventory.setEnabled(false);


        inventory.setVersion(
                inventory.getVersion()+1
        );


        repository.save(
                inventory
        );



        auditService.save(
                "SYSTEM",
                "DISABLE_INVENTORY",
                inventory.getId().toString(),
                "Inventory disabled"
        );


    }


}