package com.inmed.auth.controller;

import com.inmed.auth.dto.AuthResponse;
import com.inmed.auth.dto.LoginRequest;
import com.inmed.auth.dto.LogoutRequest;
import com.inmed.auth.dto.RefreshRequest;
import com.inmed.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;
import com.inmed.auth.dto.ActiveSessionResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(
            @Valid
            @RequestBody
            LoginRequest request
    ) {

        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AuthResponse refreshToken(
            @RequestBody
            RefreshRequest request
    ) {

        return authService.refreshToken(
                request.getRefreshToken()
        );
    }

    @PostMapping("/logout")
    public String logout(
            @RequestBody LogoutRequest request
    ) {

        authService.logout(
                request.getRefreshToken()
        );

        return "Logout successful";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sessions")
    public List<ActiveSessionResponse>
    getSessions() {

        return authService
                .getActiveSessions();
    }
}