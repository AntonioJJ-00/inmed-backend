package com.inmed.inventory.dto.request;


import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdateInventoryRequest {


    @Min(0)
    private Integer stock;


    @Min(0)
    private Integer reservedStock;


    @Min(0)
    private Integer minimumStock;


    @Min(0)
    private Integer maximumStock;


    private Boolean enabled;


}