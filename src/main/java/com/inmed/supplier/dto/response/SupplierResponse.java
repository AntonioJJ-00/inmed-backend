package com.inmed.supplier.dto.response;


import com.inmed.supplier.entity.SupplierStatus;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SupplierResponse {

    private Long id;

    private String code;

    private String name;

    private String taxId;

    private String contactName;

    private String phone;

    private String email;

    private String address;

    private SupplierStatus status;

    private Long companyId;

    private Long syncVersion;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}