package com.inmed.inventory.mapper;


import com.inmed.inventory.dto.response.InventoryResponse;
import com.inmed.inventory.entity.Inventory;


public final class InventoryMapper {


    private InventoryMapper() {

    }



    public static InventoryResponse toResponse(
            Inventory inventory
    ) {


        return InventoryResponse.builder()


                .id(
                        inventory.getId()
                )


                .branchId(
                        inventory.getBranch().getId()
                )


                .branchName(
                        inventory.getBranch().getName()
                )


                .productId(
                        inventory.getProduct().getId()
                )


                .productName(
                        inventory.getProduct().getName()
                )


                .stock(
                        inventory.getStock()
                )


                .reservedStock(
                        inventory.getReservedStock()
                )


                .availableStock(
                        calculateAvailableStock(
                                inventory
                        )
                )


                .minimumStock(
                        inventory.getMinimumStock()
                )


                .maximumStock(
                        inventory.getMaximumStock()
                )


                .lastMovement(
                        inventory.getLastMovement()
                )


                .lastSync(
                        inventory.getLastSync()
                )


                .version(
                        inventory.getVersion()
                )


                .enabled(
                        inventory.getEnabled()
                )


                .createdAt(
                        inventory.getCreatedAt()
                )


                .updatedAt(
                        inventory.getUpdatedAt()
                )


                .build();

    }



    private static Integer calculateAvailableStock(
            Inventory inventory
    ){

        Integer stock =
                inventory.getStock() == null
                        ? 0
                        : inventory.getStock();



        Integer reserved =
                inventory.getReservedStock() == null
                        ? 0
                        : inventory.getReservedStock();



        return stock - reserved;

    }

}