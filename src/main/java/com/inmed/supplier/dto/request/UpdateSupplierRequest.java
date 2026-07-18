package com.inmed.supplier.dto.request;


import com.inmed.supplier.entity.SupplierStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSupplierRequest {
    private String name;

    private String taxId;

    private String contactName;

    private String phone;

    private String email;

    private String address;

    private SupplierStatus status;
}