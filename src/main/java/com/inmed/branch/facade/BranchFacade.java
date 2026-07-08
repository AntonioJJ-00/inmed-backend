package com.inmed.branch.facade;

import com.inmed.branch.dto.request.CreateBranchRequest;
import com.inmed.branch.dto.request.UpdateBranchRequest;
import com.inmed.branch.dto.response.BranchResponse;

import java.util.List;

public interface BranchFacade {

    BranchResponse create(CreateBranchRequest request);

    List<BranchResponse> findAll();

    BranchResponse findById(Long id);

    BranchResponse update(
            Long id,
            UpdateBranchRequest request
    );

    void suspend(Long id);

}