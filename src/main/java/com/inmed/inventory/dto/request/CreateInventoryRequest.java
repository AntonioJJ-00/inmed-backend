package com.inmed.inventory.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateInventoryRequest {


    @NotNull
    private Long branchId;


    @NotNull
    private Long productId;


    @Min(0)
    private Integer stock;


    @Min(0)
    private Integer reservedStock;


    @Min(0)
    private Integer minimumStock;


    @Min(0)
    private Integer maximumStock;


}