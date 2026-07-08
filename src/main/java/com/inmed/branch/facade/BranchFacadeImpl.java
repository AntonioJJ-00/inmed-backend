package com.inmed.branch.facade;

import com.inmed.branch.dto.request.CreateBranchRequest;
import com.inmed.branch.dto.request.UpdateBranchRequest;
import com.inmed.branch.dto.response.BranchResponse;
import com.inmed.branch.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BranchFacadeImpl implements BranchFacade {

    private final BranchService service;

    @Override
    public BranchResponse create(CreateBranchRequest request) {
        return service.create(request);
    }

    @Override
    public List<BranchResponse> findAll() {
        return service.findAll();
    }

    @Override
    public BranchResponse findById(Long id) {
        return service.findById(id);
    }

    @Override
    public BranchResponse update(Long id,
                                 UpdateBranchRequest request) {

        return service.update(id, request);
    }

    @Override
    public void suspend(Long id) {
        service.suspend(id);
    }
}