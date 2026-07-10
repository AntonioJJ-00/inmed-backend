package com.inmed.posauth.facade;

import com.inmed.posauth.dto.request.PosLoginRequest;
import com.inmed.posauth.dto.response.PosLoginResponse;

public interface PosAuthFacade {
    PosLoginResponse login(
            PosLoginRequest request
    );
}