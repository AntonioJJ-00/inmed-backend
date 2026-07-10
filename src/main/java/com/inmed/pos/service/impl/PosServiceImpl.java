package com.inmed.pos.service.impl;

import com.inmed.audit.service.AuditService;
import org.springframework.security.core.context.SecurityContextHolder;
import com.inmed.branch.entity.Branch;
import com.inmed.branch.repository.BranchRepository;
import com.inmed.exception.custom.DuplicateResourceException;
import com.inmed.exception.custom.ResourceNotFoundException;
import com.inmed.pos.dto.request.CreatePosRequest;
import com.inmed.pos.dto.request.UpdatePosRequest;
import com.inmed.pos.dto.response.PosResponse;
import com.inmed.pos.entity.PosDevice;
import com.inmed.pos.mapper.PosMapper;
import com.inmed.pos.repository.PosRepository;
import com.inmed.pos.service.PosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PosServiceImpl implements PosService {

    private final PosRepository posRepository;
    private final BranchRepository branchRepository;
    private final AuditService auditService;

    @Override
    public PosResponse createPos(CreatePosRequest request) {
        if(posRepository.existsBySerialNumber(
                request.getSerialNumber()
        )){
            throw new DuplicateResourceException(
                    "Serial number already exists"
            );
        }

        if(posRepository.existsByCode(
                request.getCode()
        )){

            throw new DuplicateResourceException(
                    "POS code already exists"
            );
        }

        Branch branch =
                branchRepository.findById(
                                request.getBranchId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Branch not found"
                                )
                        );
        PosDevice pos =
                PosDevice.builder()
                        .branch(branch)
                        .code(request.getCode())
                        .name(request.getName())
                        .serialNumber(request.getSerialNumber())
                        .appVersion(request.getAppVersion())
                        .enabled(true)
                        .build();

        PosDevice saved =
                posRepository.save(pos);

        auditService.save(
                getCurrentUser(),
                "CREATE_POS",
                saved.getCode(),
                "POS created in branch: "
                        + saved.getBranch().getName()
        );
        return PosMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PosResponse> getAll(){
        return posRepository.findAll()
                .stream()
                .map(PosMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PosResponse getById(Long id){
        PosDevice pos =
                posRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "POS not found"
                                )
                        );
        return PosMapper.toResponse(pos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PosResponse> getByBranch(Long branchId){

        Branch branch =
                branchRepository.findById(branchId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Branch not found"
                                )
                        );

        return posRepository.findByBranch(branch)
                .stream()
                .map(PosMapper::toResponse)
                .toList();
    }

    @Override
    public PosResponse update(
            Long id,
            UpdatePosRequest request
    ){
        PosDevice pos =
                posRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "POS not found"
                                )
                        );

        pos.setCode(
                request.getCode()
        );

        pos.setName(
                request.getName()
        );

        pos.setAppVersion(
                request.getAppVersion()
        );

        pos.setStatus(
                request.getStatus()
        );

        pos.setEnabled(
                request.getEnabled()
        );

        PosDevice updated =
                posRepository.save(pos);
        auditService.save(
                getCurrentUser(),
                "UPDATE_POS",
                updated.getCode(),
                "POS updated"
        );
        return PosMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id){
        PosDevice pos =
                posRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "POS not found"
                                )
                        );

        pos.setEnabled(false);
        PosDevice disabled =
                posRepository.save(pos);
        auditService.save(
                getCurrentUser(),
                "DISABLE_POS",
                disabled.getCode(),
                "POS disabled"
        );

    }
    private String getCurrentUser(){

        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

    }
}