package com.inmed.auth.controller;

import com.inmed.auth.dto.*;
import com.inmed.auth.facade.AuthFacade;
import com.inmed.auth.repository.LoginAuditRepository;
import com.inmed.auth.service.AuthService;
import com.inmed.common.response.ApiResponse;
import com.inmed.common.response.ResponseFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthFacade authFacade;
    private final LoginAuditRepository loginAuditRepository;

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest servletRequest
    ) {

        System.out.println("========== LOGIN CONTROLLER ==========");

        String ip = servletRequest.getRemoteAddr();

        AuthResponse response =
                authFacade.login(request, ip);

        return ResponseFactory.success(
                "Login successful",
                response
        );
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refreshToken(

            @RequestBody RefreshRequest request,

            HttpServletRequest servletRequest

    ){

        String ip =
                servletRequest.getRemoteAddr();


        String device =
                servletRequest.getHeader(
                        "User-Agent"
                );


        AuthResponse response =
                authFacade.refreshToken(
                        request.getRefreshToken(),
                        ip,
                        device
                );


        return ResponseFactory.success(
                "Token refreshed successfully",
                response
        );

    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(
            @RequestBody LogoutRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        authFacade.logout(
                request.getRefreshToken(),
                token
        );
        return ResponseFactory.success("Logout successful");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sessions")
    public ApiResponse<List<ActiveSessionResponse>> getSessions() {

        return ResponseFactory.success(
                "Active sessions retrieved successfully",
                authFacade.getActiveSessions()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/force-logout")
    public ApiResponse<Void> forceLogout(
            @Valid
            @RequestBody ForceLogoutRequest request
    ) {

        authFacade.forceLogout(
                request.getUsername()
        );

        return ResponseFactory.success(
                "User session terminated"
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/login-history")
    public ApiResponse<List<LoginAuditResponse>> getLoginHistory() {

        List<LoginAuditResponse> response =
                loginAuditRepository.findAll()
                        .stream()
                        .map(audit ->
                                LoginAuditResponse.builder()
                                        .id(audit.getId())
                                        .username(audit.getUsername())
                                        .status(audit.getStatus())
                                        .ipAddress(audit.getIpAddress())
                                        .createdAt(audit.getCreatedAt().toString())
                                        .build()
                        )
                        .toList();

        return ResponseFactory.success(
                "Login history retrieved successfully",
                response
        );
    }
}