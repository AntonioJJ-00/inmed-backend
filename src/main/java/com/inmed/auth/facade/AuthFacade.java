package com.inmed.auth.facade;

import com.inmed.auth.dto.*;
import com.inmed.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final AuthService authService;

    public AuthResponse login(
            LoginRequest request,
            String ipAddress
    ) {
        return authService.login(request, ipAddress);
    }

    public AuthResponse refreshToken(
            String refreshToken,
            String ip,
            String device
    ){

        return authService.refreshToken(
                refreshToken,
                ip,
                device
        );

    }

    public void logout(String refreshToken, String accessToken) {
        authService.logout(refreshToken, accessToken);
    }

    public List<ActiveSessionResponse> getActiveSessions() {
        return authService.getActiveSessions();
    }

    public void forceLogout(
            String username
    ) {
        authService.forceLogout(username);
    }

}