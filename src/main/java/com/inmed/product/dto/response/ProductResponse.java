package com.inmed.product.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class ProductResponse {

    private Long id;

    private String code;

    private String name;

    private String shortDescription;

    private String longDescription;

    private BigDecimal cost;

    private BigDecimal salePrice;

    private BigDecimal wholesalePrice;

    private Integer relativeGain;

    private Boolean active;

    private Integer syncVersion;

    private Long categoryId;

    private String categoryName;

    private Long supplierId;

    private String supplierName;

    private Long companyId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}