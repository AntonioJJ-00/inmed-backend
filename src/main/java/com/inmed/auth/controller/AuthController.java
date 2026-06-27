package com.inmed.auth.controller;

import com.inmed.auth.dto.*;
import com.inmed.auth.entity.LoginAudit;
import com.inmed.auth.repository.LoginAuditRepository;
import com.inmed.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final LoginAuditRepository loginAuditRepository;

    @PostMapping("/login")
    public AuthResponse login(
            @Valid
            @RequestBody LoginRequest request,
            HttpServletRequest servletRequest
    ) {

        String ip =
                servletRequest.getRemoteAddr();

        return authService.login(
                request,
                ip
        );
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/force-logout")
    public String forceLogout(
            @Valid
            @RequestBody
            ForceLogoutRequest request
    ) {

        authService.forceLogout(
                request.getUsername()
        );

        return "User session terminated";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/login-history")
    public List<LoginAuditResponse> getLoginHistory() {

        return loginAuditRepository.findAll()
                .stream()
                .map(audit -> LoginAuditResponse.builder()
                        .id(audit.getId())
                        .username(audit.getUsername())
                        .status(audit.getStatus())
                        .ipAddress(audit.getIpAddress())
                        .createdAt(audit.getCreatedAt().toString())
                        .build()
                )
                .toList();
    }
}