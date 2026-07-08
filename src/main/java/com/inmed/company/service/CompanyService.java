package com.inmed.company.service;


import com.inmed.company.dto.request.UpdateCompanyRequest;
import com.inmed.company.dto.response.CompanyResponse;
import com.inmed.company.entity.Company;


public interface CompanyService {
    //CompanyResponse getCompany();
    Company getCompany();
    CompanyResponse updateCompany(
            UpdateCompanyRequest request
    );

}