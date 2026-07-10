package com.inmed.PosCredential.service.impl;

import com.inmed.exception.custom.DuplicateResourceException;
import com.inmed.exception.custom.ResourceNotFoundException;
import com.inmed.pos.entity.PosDevice;
import com.inmed.pos.repository.PosRepository;
import com.inmed.PosCredential.dto.request.CreatePosCredentialRequest;
import com.inmed.PosCredential.dto.response.PosCredentialResponse;
import com.inmed.PosCredential.entity.PosCredential;
import com.inmed.PosCredential.mapper.PosCredentialMapper;
import com.inmed.PosCredential.repository.PosCredentialRepository;
import com.inmed.PosCredential.service.PosCredentialService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PosCredentialServiceImpl
        implements PosCredentialService {

    private final PosCredentialRepository credentialRepository;

    private final PosRepository posRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public PosCredentialResponse create(
            CreatePosCredentialRequest request
    ){

        if(credentialRepository.existsByUsername(
                request.getUsername()
        )){
            throw new DuplicateResourceException(
                    "Username already exists"
            );
        }

        PosDevice pos =
                posRepository.findById(
                                request.getPosId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "POS not found"
                                )
                        );

        if(credentialRepository.existsByPosDevice(pos)){
            throw new DuplicateResourceException(
                    "POS already has credentials"
            );
        }

        PosCredential credential =
                PosCredential.builder()
                        .posDevice(pos)
                        .username(
                                request.getUsername()
                        )
                        .password(
                                passwordEncoder.encode(
                                        request.getPassword()
                                )
                        )
                        .enabled(true)
                        .build();
        PosCredential saved =
                credentialRepository.save(
                        credential
                );
        return PosCredentialMapper.toResponse(
                saved
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<PosCredentialResponse> getAll(){
        return credentialRepository.findAll()
                .stream()
                .map(PosCredentialMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PosCredentialResponse getById(
            Long id
    ){
        PosCredential credential =
                credentialRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Credential not found"
                                )
                        );

        return PosCredentialMapper.toResponse(
                credential
        );
    }

    @Override
    public void disable(
            Long id
    ){
        PosCredential credential =
                credentialRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Credential not found"
                                )
                        );

        credential.setEnabled(false);

        credentialRepository.save(
                credential
        );
    }
}