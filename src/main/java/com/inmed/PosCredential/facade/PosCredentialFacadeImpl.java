package com.inmed.PosCredential.facade;

import com.inmed.PosCredential.dto.request.CreatePosCredentialRequest;
import com.inmed.PosCredential.dto.response.PosCredentialResponse;
import com.inmed.PosCredential.service.PosCredentialService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PosCredentialFacadeImpl
        implements PosCredentialFacade {

    private final PosCredentialService credentialService;

    @Override
    public PosCredentialResponse create(
            CreatePosCredentialRequest request
    ){
        return credentialService.create(
                request
        );
    }

    @Override
    public List<PosCredentialResponse> getAll(){
        return credentialService.getAll();
    }

    @Override
    public PosCredentialResponse getById(
            Long id
    ){
        return credentialService.getById(
                id
        );
    }

    @Override
    public void disable(
            Long id
    ){
        credentialService.disable(
                id
        );
    }
}