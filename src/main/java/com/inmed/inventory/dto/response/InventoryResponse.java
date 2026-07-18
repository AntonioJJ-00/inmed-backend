package com.inmed.inventory.dto.response;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class InventoryResponse {


    private Long id;


    private Long branchId;


    private String branchName;


    private Long productId;


    private String productName;


    private Integer stock;


    private Integer reservedStock;


    private Integer availableStock;


    private Integer minimumStock;


    private Integer maximumStock;


    private LocalDateTime lastMovement;


    private LocalDateTime lastSync;


    private Long version;


    private Boolean enabled;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;


}