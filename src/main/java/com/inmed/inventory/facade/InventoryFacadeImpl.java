package com.inmed.inventory.facade;


import com.inmed.inventory.dto.request.CreateInventoryRequest;
import com.inmed.inventory.dto.request.UpdateInventoryRequest;
import com.inmed.inventory.dto.response.InventoryResponse;
import com.inmed.inventory.service.InventoryService;


import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Component;


import java.util.List;



@Component
@RequiredArgsConstructor
public class InventoryFacadeImpl
        implements InventoryFacade {



    private final InventoryService service;




    @Override
    public InventoryResponse create(
            CreateInventoryRequest request
    ){

        return service.create(request);

    }





    @Override
    public InventoryResponse findById(
            Long id
    ){

        return service.findById(id);

    }





    @Override
    public List<InventoryResponse> findAll(){

        return service.findAll();

    }





    @Override
    public List<InventoryResponse> findByBranch(
            Long branchId
    ){

        return service.findByBranch(branchId);

    }





    @Override
    public List<InventoryResponse> findByProduct(
            Long productId
    ){

        return service.findByProduct(productId);

    }





    @Override
    public InventoryResponse update(
            Long id,
            UpdateInventoryRequest request
    ){

        return service.update(
                id,
                request
        );

    }





    @Override
    public void disable(
            Long id
    ){

        service.disable(id);

    }


}