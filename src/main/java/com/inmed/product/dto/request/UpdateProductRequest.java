package com.inmed.product.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateProductRequest {

    private String name;

    private String shortDescription;

    private String longDescription;

    private BigDecimal cost;

    private BigDecimal salePrice;

    private BigDecimal wholesalePrice;

    private Integer relativeGain;

    private Long categoryId;

    private Long supplierId;
}