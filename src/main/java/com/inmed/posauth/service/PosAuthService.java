package com.inmed.posauth.service;

import com.inmed.posauth.dto.request.PosLoginRequest;
import com.inmed.posauth.dto.response.PosLoginResponse;

public interface PosAuthService {
    PosLoginResponse login(
            PosLoginRequest request
    );
}