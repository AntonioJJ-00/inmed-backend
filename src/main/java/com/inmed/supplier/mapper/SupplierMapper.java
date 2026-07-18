package com.inmed.supplier.mapper;

import com.inmed.supplier.dto.response.SupplierResponse;
import com.inmed.supplier.entity.Supplier;

public final class SupplierMapper {

    private SupplierMapper(){
    }

    public static SupplierResponse toResponse(
            Supplier supplier
    ){

        return SupplierResponse.builder()
                .id(
                        supplier.getId()
                )
                .code(
                        supplier.getCode()
                )
                .name(
                        supplier.getName()
                )
                .taxId(
                        supplier.getTaxId()
                )
                .contactName(
                        supplier.getContactName()
                )
                .phone(
                        supplier.getPhone()
                )
                .email(
                        supplier.getEmail()
                )
                .address(
                        supplier.getAddress()
                )
                .status(
                        supplier.getStatus()
                )
                .companyId(
                        supplier.getCompany()
                                .getId()
                )
                .syncVersion(
                        supplier.getSyncVersion()
                )
                .createdAt(
                        supplier.getCreatedAt()
                )
                .updatedAt(
                        supplier.getUpdatedAt()
                )
                .build();
    }
}