package com.inmed.branch.service.impl;

import com.inmed.audit.service.AuditService;
import com.inmed.branch.dto.request.CreateBranchRequest;
import com.inmed.branch.dto.request.UpdateBranchRequest;
import com.inmed.branch.dto.response.BranchResponse;
import com.inmed.branch.entity.Branch;
import com.inmed.branch.entity.BranchStatus;
import com.inmed.branch.mapper.BranchMapper;
import com.inmed.branch.repository.BranchRepository;
import com.inmed.branch.service.BranchService;
import com.inmed.company.entity.Company;
import com.inmed.company.facade.CompanyFacade;
import com.inmed.company.repository.CompanyRepository;
import com.inmed.exception.custom.DuplicateResourceException;
import com.inmed.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BranchServiceImpl implements BranchService {

    private final BranchRepository repository;
    private final CompanyRepository companyRepository;
    private final AuditService auditService;
    private final CompanyFacade companyFacade;

    Company company = companyFacade.getCompany();

    @Override
    public BranchResponse create(CreateBranchRequest request) {

        if (repository.existsByCode(request.getCode())) {
            throw new DuplicateResourceException("Branch code already exists");
        }

        Company company = companyRepository
                .findTopByOrderByIdAsc()
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found"));


        Branch branch = Branch.builder()
                .name(request.getName())
                .code(request.getCode())
                .address(request.getAddress())
                .phone(request.getPhone())
                .status(BranchStatus.ACTIVE)
                .company(company)
                .build();

        repository.save(branch);

        auditService.save(
                "SYSTEM",
                "CREATE_BRANCH",
                branch.getCode(),
                "Branch created"
        );

        return BranchMapper.toResponse(branch);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(BranchMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BranchResponse findById(Long id) {

        Branch branch = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Branch not found"));

        return BranchMapper.toResponse(branch);
    }

    @Override
    public BranchResponse update(
            Long id,
            UpdateBranchRequest request
    ) {

        Branch branch = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Branch not found"));

        if (request.getName() != null)
            branch.setName(request.getName());

        if (request.getAddress() != null)
            branch.setAddress(request.getAddress());

        if (request.getPhone() != null)
            branch.setPhone(request.getPhone());

        if (request.getStatus() != null)
            branch.setStatus(request.getStatus());

        repository.save(branch);

        auditService.save(
                "SYSTEM",
                "UPDATE_BRANCH",
                branch.getCode(),
                "Branch updated"
        );

        return BranchMapper.toResponse(branch);
    }

    @Override
    public void suspend(Long id) {

        Branch branch = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Branch not found"));

        branch.setStatus(BranchStatus.SUSPENDED);

        repository.save(branch);

        auditService.save(
                "SYSTEM",
                "SUSPEND_BRANCH",
                branch.getCode(),
                "Branch suspended"
        );
    }
}