package com.inmed.posauth.service.impl;


import com.inmed.exception.custom.ResourceNotFoundException;

import com.inmed.posauth.dto.request.PosLoginRequest;
import com.inmed.posauth.dto.response.PosLoginResponse;

import com.inmed.PosCredential.entity.PosCredential;
import com.inmed.PosCredential.repository.PosCredentialRepository;
import com.inmed.posauth.service.PosAuthService;
import com.inmed.security.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Transactional
public class PosAuthServiceImpl
        implements PosAuthService {


    private final PosCredentialRepository credentialRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public PosLoginResponse login(
            PosLoginRequest request
    ){

        PosCredential credential =
                credentialRepository.findByUsername(
                                request.getUsername()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Invalid POS credentials"
                                )
                        );

        if(!credential.getEnabled()){
            throw new RuntimeException(
                    "POS disabled"
            );
        }

        boolean passwordValid =
                passwordEncoder.matches(
                        request.getPassword(),
                        credential.getPassword()
                );

        if(!passwordValid){
            throw new RuntimeException(
                    "Invalid POS credentials"
            );
        }

        credential.setLastLogin(LocalDateTime.now());
        credentialRepository.save(credential);

        String accessToken = jwtService.generateToken(
                credential.getUsername(),
                "ROLE_POS"
        );

        return PosLoginResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(3600L)
                .posId(credential.getPosDevice().getId())
                .branchId(credential.getPosDevice().getBranch().getId())
                .branchName(credential.getPosDevice().getBranch().getName())
                .posCode(credential.getPosDevice().getCode())
                .build();
    }
}