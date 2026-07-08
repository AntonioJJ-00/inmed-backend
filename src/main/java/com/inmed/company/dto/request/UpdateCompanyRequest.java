package com.inmed.company.dto.request;


import com.inmed.company.entity.CompanyStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdateCompanyRequest {


    @NotBlank
    private String businessName;


    @NotBlank
    private String tradeName;


    @NotBlank
    private String taxId;


    @Email
    private String email;


    private String phone;


    private String website;


    private CompanyStatus status;

}