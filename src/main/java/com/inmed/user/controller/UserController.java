package com.inmed.user.controller;

import com.inmed.user.dto.CreateUserRequest;
import com.inmed.user.dto.UserResponse;
import com.inmed.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.inmed.user.dto.UserStatusRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import com.inmed.user.dto.ChangePasswordRequest;
import com.inmed.user.dto.UpdateUserRequest;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public UserResponse createUser(
            @Valid @RequestBody CreateUserRequest request
    ) {

        return userService.createUser(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public UserResponse getUserById(
            @PathVariable Long id
    ) {
        return userService.getUserById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable Long id,
            @Valid
            @RequestBody UpdateUserRequest request
    ) {

        return userService.updateUser(
                id,
                request
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteUser(
            @PathVariable Long id
    ) {

        userService.deleteUser(id);

        return "User deleted successfully";
    }

    @GetMapping("/test")
    public String test() {
        return "Backend funcionando";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/block")
    public String blockUser(
            @Valid
            @RequestBody UserStatusRequest request
    ) {

        userService.blockUser(
                request.getUsername()
        );

        return "User blocked";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/unblock")
    public String unblockUser(
            @Valid
            @RequestBody UserStatusRequest request
    ) {

        userService.unblockUser(
                request.getUsername()
        );

        return "User unblocked";
    }

    @PostMapping("/change-password")
    public String changePassword(
            Authentication authentication,
            @Valid
            @RequestBody ChangePasswordRequest request
    ) {

        userService.changePassword(
                authentication.getName(),
                request.getCurrentPassword(),
                request.getNewPassword()
        );

        return "Password changed successfully";
    }
}
