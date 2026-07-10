package com.inmed.PosCredential.facade;

import com.inmed.PosCredential.dto.request.CreatePosCredentialRequest;
import com.inmed.PosCredential.dto.response.PosCredentialResponse;

import java.util.List;

public interface PosCredentialFacade {

    PosCredentialResponse create(
            CreatePosCredentialRequest request
    );

    List<PosCredentialResponse> getAll();

    PosCredentialResponse getById(
            Long id
    );

    void disable(
            Long id
    );
}