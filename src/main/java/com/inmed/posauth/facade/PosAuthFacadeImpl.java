package com.inmed.posauth.facade;

import com.inmed.posauth.dto.request.PosLoginRequest;
import com.inmed.posauth.dto.response.PosLoginResponse;
import com.inmed.posauth.service.PosAuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PosAuthFacadeImpl
        implements PosAuthFacade {

    private final PosAuthService posAuthService;

    @Override
    public PosLoginResponse login(
            PosLoginRequest request
    ){
        return posAuthService.login(request);
    }
}