package com.inmed.company.controller;


import com.inmed.company.dto.request.UpdateCompanyRequest;
import com.inmed.company.dto.response.CompanyResponse;
import com.inmed.company.entity.Company;
import com.inmed.company.facade.CompanyFacade;
import com.inmed.common.response.ApiResponse;
import com.inmed.common.response.ResponseFactory;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {


    private final CompanyFacade facade;



    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Company> get(){


        return ResponseFactory.success(
                "Company retrieved",
                facade.getCompany()
        );

    }


    @PatchMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<CompanyResponse> update(
            @Valid
            @RequestBody UpdateCompanyRequest request
    ){


        return ResponseFactory.success(
                "Company updated",
                facade.updateCompany(request)
        );

    }

}