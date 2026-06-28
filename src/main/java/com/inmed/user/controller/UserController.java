package com.inmed.user.controller;

import com.inmed.common.response.ApiSuccessResponse;
import com.inmed.user.dto.*;
import com.inmed.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiSuccessResponse<UserResponse>> createUser(
            @Valid @RequestBody CreateUserRequest request
    ) {

        UserResponse user = userService.createUser(request);

        return ResponseEntity.ok(
                ApiSuccessResponse.<UserResponse>builder()
                        .success(true)
                        .message("User created successfully")
                        .data(user)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiSuccessResponse<List<UserResponse>>> getAllUsers() {

        List<UserResponse> users = userService.getAllUsers();

        return ResponseEntity.ok(
                ApiSuccessResponse.<List<UserResponse>>builder()
                        .success(true)
                        .message("Users retrieved successfully")
                        .data(users)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<UserResponse>> getUserById(
            @PathVariable Long id
    ) {

        UserResponse user = userService.getUserById(id);

        return ResponseEntity.ok(
                ApiSuccessResponse.<UserResponse>builder()
                        .success(true)
                        .message("User retrieved successfully")
                        .data(user)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/test")
    public ResponseEntity<ApiSuccessResponse<String>> test() {

        return ResponseEntity.ok(
                ApiSuccessResponse.<String>builder()
                        .success(true)
                        .message("Backend running")
                        .data("Backend funcionando")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/block")
    public ResponseEntity<ApiSuccessResponse<Void>> blockUser(
            @Valid @RequestBody UserStatusRequest request
    ) {

        userService.blockUser(request.getUsername());

        return ResponseEntity.ok(
                ApiSuccessResponse.<Void>builder()
                        .success(true)
                        .message("User blocked successfully")
                        .data(null)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/unblock")
    public ResponseEntity<ApiSuccessResponse<Void>> unblockUser(
            @Valid @RequestBody UserStatusRequest request
    ) {

        userService.unblockUser(request.getUsername());

        return ResponseEntity.ok(
                ApiSuccessResponse.<Void>builder()
                        .success(true)
                        .message("User unblocked successfully")
                        .data(null)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiSuccessResponse<Void>> changePassword(
            Authentication authentication,
            @Valid @RequestBody ChangePasswordRequest request
    ) {

        userService.changePassword(
                authentication.getName(),
                request.getCurrentPassword(),
                request.getNewPassword()
        );

        return ResponseEntity.ok(
                ApiSuccessResponse.<Void>builder()
                        .success(true)
                        .message("Password changed successfully")
                        .data(null)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}