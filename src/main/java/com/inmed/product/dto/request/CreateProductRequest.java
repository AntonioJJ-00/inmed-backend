package com.inmed.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateProductRequest {

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    private String shortDescription;

    private String longDescription;

    @NotNull
    private BigDecimal cost;

    @NotNull
    private BigDecimal salePrice;

    private BigDecimal wholesalePrice;

    private Integer relativeGain;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long supplierId;
}