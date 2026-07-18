package com.inmed.supplier.service.impl;

import com.inmed.audit.service.AuditService;
import com.inmed.company.entity.Company;
import com.inmed.company.repository.CompanyRepository;

import com.inmed.exception.custom.DuplicateResourceException;
import com.inmed.exception.custom.ResourceNotFoundException;

import com.inmed.supplier.dto.request.CreateSupplierRequest;
import com.inmed.supplier.dto.request.UpdateSupplierRequest;
import com.inmed.supplier.dto.response.SupplierResponse;

import com.inmed.supplier.entity.Supplier;
import com.inmed.supplier.entity.SupplierStatus;

import com.inmed.supplier.mapper.SupplierMapper;
import com.inmed.supplier.repository.SupplierRepository;
import com.inmed.supplier.service.SupplierService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierServiceImpl
        implements SupplierService {

    private final SupplierRepository repository;

    private final CompanyRepository companyRepository;

    private final AuditService auditService;

    @Override
    public SupplierResponse create(
            CreateSupplierRequest request
    ) {
        if(repository.existsByCode(request.getCode())){
            throw new DuplicateResourceException(
                    "Supplier code already exists"
            );

        }
        Company company =
                companyRepository
                        .findTopByOrderByIdAsc()
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Company not found"
                                )
                        );
        Supplier supplier =
                Supplier.builder()
                        .code(
                                request.getCode()
                        )
                        .name(
                                request.getName()
                        )
                        .taxId(
                                request.getTaxId()
                        )
                        .contactName(
                                request.getContactName()
                        )
                        .phone(
                                request.getPhone()
                        )
                        .email(
                                request.getEmail()
                        )
                        .address(
                                request.getAddress()
                        )
                        .status(
                                SupplierStatus.ACTIVE
                        )
                        .company(
                                company
                        )
                        .build();
        repository.save(
                supplier
        );
        auditService.save(
                "SYSTEM",
                "CREATE_SUPPLIER",
                supplier.getCode(),
                "Supplier created"
        );
        return SupplierMapper.toResponse(
                supplier
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupplierResponse> findAll(){

        return repository.findAll()
                .stream()
                .map(
                        SupplierMapper::toResponse
                )
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SupplierResponse findById(
            Long id
    ){
        Supplier supplier =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Supplier not found"
                                )
                        );
        return SupplierMapper.toResponse(
                supplier
        );
    }

    @Override
    public SupplierResponse update(
            Long id,
            UpdateSupplierRequest request
    ){
        Supplier supplier =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Supplier not found"
                                )
                        );
        if(request.getName()!=null){
            supplier.setName(
                    request.getName()
            );
        }

        if(request.getTaxId()!=null){
            supplier.setTaxId(
                    request.getTaxId()
            );
        }

        if(request.getContactName()!=null){
            supplier.setContactName(
                    request.getContactName()
            );
        }

        if(request.getPhone()!=null){
            supplier.setPhone(
                    request.getPhone()
            );
        }

        if(request.getEmail()!=null){
            supplier.setEmail(
                    request.getEmail()
            );
        }

        if(request.getAddress()!=null){
            supplier.setAddress(
                    request.getAddress()
            );
        }
        if(request.getStatus()!=null){
            supplier.setStatus(
                    request.getStatus()
            );
        }
        repository.save(
                supplier
        );
        auditService.save(
                "SYSTEM",
                "UPDATE_SUPPLIER",
                supplier.getCode(),
                "Supplier updated"
        );
        return SupplierMapper.toResponse(
                supplier
        );
    }

    @Override
    public void deactivate(
            Long id
    ){
        Supplier supplier =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Supplier not found"
                                )
                        );

        supplier.setStatus(
                SupplierStatus.INACTIVE
        );

        repository.save(
                supplier
        );

        auditService.save(
                "SYSTEM",
                "DEACTIVATE_SUPPLIER",
                supplier.getCode(),
                "Supplier deactivated"
        );
    }
}