package com.inmed.pos.facade;

import com.inmed.pos.dto.request.CreatePosRequest;
import com.inmed.pos.dto.request.UpdatePosRequest;
import com.inmed.pos.dto.response.PosResponse;

import java.util.List;

public interface PosFacade {
    PosResponse createPos(
            CreatePosRequest request
    );

    List<PosResponse> getAll();
    PosResponse getById(
            Long id
    );

    List<PosResponse> getByBranch(
            Long branchId
    );

    PosResponse update(
            Long id,
            UpdatePosRequest request
    );

    void delete(
            Long id
    );
}