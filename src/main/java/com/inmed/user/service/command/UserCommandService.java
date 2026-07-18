package com.inmed.user.service.command;

import com.inmed.audit.service.AuditService;
import com.inmed.exception.custom.*;
import com.inmed.security.PasswordPolicyValidator;
import com.inmed.user.dto.*;
import com.inmed.user.entity.User;
import com.inmed.user.mapper.UserMapper;
import com.inmed.user.repository.UserRepository;
import com.inmed.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditService auditService;
    private final RefreshTokenService refreshTokenService;

    private String getAdmin() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    private void invalidate(User user) {
        refreshTokenService.revokeAll(user);
    }

    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }

        if (!PasswordPolicyValidator.isValid(request.getPassword())) {
            throw new WeakPasswordException("Weak password");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .enabled(true)
                .failedLoginAttempts(0)
                .build();

        return UserMapper.toResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (request.getFirstName() != null)
            user.setFirstName(request.getFirstName());

        if (request.getLastName() != null)
            user.setLastName(request.getLastName());

        if (request.getRole() != null) {
            user.setRole(request.getRole());
            invalidate(user);
        }

        if (request.getEnabled() != null) {
            user.setEnabled(request.getEnabled());
            invalidate(user);
        }

        auditService.save(
                getAdmin(),
                "UPDATE_USER",
                user.getUsername(),
                "User updated"
        );

        return UserMapper.toResponse(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String admin = getAdmin();

        if (admin.equals(user.getUsername())) {
            throw new IllegalArgumentException("Cannot delete yourself");
        }

        invalidate(user);

        auditService.save(
                admin,
                "DELETE_USER",
                user.getUsername(),
                "User deleted"
        );

        userRepository.delete(user);
    }
}