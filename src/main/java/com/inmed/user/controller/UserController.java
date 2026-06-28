package com.inmed.user.controller;

import com.inmed.common.response.ApiResponse;
import com.inmed.common.response.ResponseFactory;
import com.inmed.user.dto.*;
import com.inmed.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<UserResponse> createUser(
            @Valid
            @RequestBody CreateUserRequest request
    ) {

        return ResponseFactory.success(
                "User created successfully",
                userService.createUser(request)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {

        return ResponseFactory.success(
                "Users retrieved successfully",
                userService.getAllUsers()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(
            @PathVariable Long id
    ) {

        return ResponseFactory.success(
                "User retrieved successfully",
                userService.getUserById(id)
        );
    }

    @GetMapping("/test")
    public ApiResponse<String> test() {

        return ResponseFactory.success(
                "Backend running",
                "Backend funcionando"
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/block")
    public ApiResponse<Void> blockUser(
            @Valid
            @RequestBody UserStatusRequest request
    ) {

        userService.blockUser(
                request.getUsername()
        );

        return ResponseFactory.success(
                "User blocked successfully"
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/unblock")
    public ApiResponse<Void> unblockUser(
            @Valid
            @RequestBody UserStatusRequest request
    ) {

        userService.unblockUser(
                request.getUsername()
        );

        return ResponseFactory.success(
                "User unblocked successfully"
        );
    }

    @PostMapping("/change-password")
    public ApiResponse<Void> changePassword(
            Authentication authentication,
            @Valid
            @RequestBody ChangePasswordRequest request
    ) {

        userService.changePassword(
                authentication.getName(),
                request.getCurrentPassword(),
                request.getNewPassword()
        );

        return ResponseFactory.success(
                "Password changed successfully"
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid
            @RequestBody UpdateUserRequest request
    ) {

        return ResponseFactory.success(
                "User updated successfully",
                userService.updateUser(id, request)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(
            @PathVariable Long id
    ) {

        userService.deleteUser(id);

        return ResponseFactory.success(
                "User deleted successfully"
        );
    }
}