package com.inmed.company.mapper;


import com.inmed.company.dto.response.CompanyResponse;
import com.inmed.company.entity.Company;



public final class CompanyMapper {


    private CompanyMapper(){}



    public static CompanyResponse toResponse(
            Company company
    ){


        return CompanyResponse.builder()

                .id(company.getId())

                .businessName(company.getBusinessName())

                .tradeName(company.getTradeName())

                .taxId(company.getTaxId())

                .email(company.getEmail())

                .phone(company.getPhone())

                .website(company.getWebsite())

                .status(company.getStatus())

                .createdAt(company.getCreatedAt())

                .updatedAt(company.getUpdatedAt())

                .build();

    }

}