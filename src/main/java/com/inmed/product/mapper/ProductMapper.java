package com.inmed.product.mapper;

import com.inmed.product.dto.response.ProductResponse;
import com.inmed.product.entity.Product;

public final class ProductMapper {

    private ProductMapper(){}

    public static ProductResponse toResponse(
            Product product
    ){
        return ProductResponse.builder()
                .id(product.getId())
                .code(product.getCode())
                .name(product.getName())
                .shortDescription(
                        product.getShortDescription()
                )
                .longDescription(
                        product.getLongDescription()
                )
                .cost(
                        product.getCost()
                )
                .salePrice(
                        product.getSalePrice()
                )
                .wholesalePrice(
                        product.getWholesalePrice()
                )
                .relativeGain(
                        product.getRelativeGain()
                )
                .active(
                        product.getActive()
                )
                .syncVersion(
                        product.getSyncVersion()
                )
                .categoryId(
                        product.getCategory().getId()
                )
                .categoryName(
                        product.getCategory().getName()
                )
                .supplierId(
                        product.getSupplier().getId()
                )
                .supplierName(
                        product.getSupplier().getName()
                )
                .companyId(
                        product.getCompany().getId()
                )
                .createdAt(
                        product.getCreatedAt()
                )
                .updatedAt(
                        product.getUpdatedAt()
                )

                .build();
    }
}