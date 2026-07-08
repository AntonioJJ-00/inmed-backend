package com.inmed.company.service.impl;


import com.inmed.company.dto.request.UpdateCompanyRequest;
import com.inmed.company.dto.response.CompanyResponse;
import com.inmed.company.entity.Company;
import com.inmed.company.mapper.CompanyMapper;
import com.inmed.company.repository.CompanyRepository;
import com.inmed.company.service.CompanyService;
import com.inmed.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CompanyServiceImpl
        implements CompanyService {

    private final CompanyRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Company getCompany() {

        return repository
                .findTopByOrderByIdAsc()
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found")
                );
    }

    @Override
    @Transactional
    public CompanyResponse updateCompany(
            UpdateCompanyRequest request
    ){
        Company company =
                repository.findTopByOrderByIdAsc()
                        .orElseThrow();

        company.setBusinessName(
                request.getBusinessName()
        );

        company.setTradeName(
                request.getTradeName()
        );

        company.setTaxId(
                request.getTaxId()
        );

        company.setEmail(
                request.getEmail()
        );

        company.setPhone(
                request.getPhone()
        );

        company.setWebsite(
                request.getWebsite()
        );

        if(request.getStatus()!=null){

            company.setStatus(
                    request.getStatus()
            );

        }

        return CompanyMapper.toResponse(
                repository.save(company)
        );

    }

}