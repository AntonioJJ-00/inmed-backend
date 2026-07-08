package com.inmed.company.facade;


import com.inmed.company.dto.request.UpdateCompanyRequest;
import com.inmed.company.dto.response.CompanyResponse;
import com.inmed.company.entity.Company;
import com.inmed.company.service.CompanyService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class CompanyFacadeImpl
        implements CompanyFacade {

    private final CompanyService service;

    @Override
    public Company getCompany() {

        return service.getCompany();
    }

    @Override
    public CompanyResponse updateCompany(
            UpdateCompanyRequest request
    ){

        return service.updateCompany(request);

    }

}