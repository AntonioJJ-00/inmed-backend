package com.inmed.supplier.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSupplierRequest {

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    private String taxId;

    private String contactName;

    private String phone;

    @Email
    private String email;
    private String address;
}