package com.inmed.pos.facade;


import com.inmed.pos.dto.request.CreatePosRequest;
import com.inmed.pos.dto.request.UpdatePosRequest;
import com.inmed.pos.dto.response.PosResponse;
import com.inmed.pos.service.PosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class PosFacadeImpl implements PosFacade {

    private final PosService posService;

    @Override
    public PosResponse createPos(
            CreatePosRequest request
    ) {
        return posService.createPos(request);
    }

    @Override
    public List<PosResponse> getAll() {
        return posService.getAll();
    }

    @Override
    public PosResponse getById(
            Long id
    ) {
        return posService.getById(id);
    }

    @Override
    public List<PosResponse> getByBranch(
            Long branchId
    ) {
        return posService.getByBranch(branchId);
    }

    @Override
    public PosResponse update(
            Long id,
            UpdatePosRequest request
    ) {
        return posService.update(
                id,
                request
        );
    }

    @Override
    public void delete(
            Long id
    ) {
        posService.delete(id);
    }
}