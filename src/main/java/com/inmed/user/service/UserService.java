package com.inmed.user.service;

import com.inmed.auth.service.RefreshTokenService;
import com.inmed.exception.custom.DuplicateResourceException;
import com.inmed.exception.custom.ResourceNotFoundException;
import com.inmed.exception.custom.WeakPasswordException;
import com.inmed.security.PasswordPolicyValidator;
import com.inmed.user.dto.CreateUserRequest;
import com.inmed.user.dto.UserResponse;
import com.inmed.user.entity.User;
import com.inmed.user.mapper.UserMapper;
import com.inmed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import com.inmed.security.SecurityConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.inmed.audit.service.AuditService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final AuditService auditService;

    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {

            throw new DuplicateResourceException(
                    "Username already exists"
            );
        }

        if (!PasswordPolicyValidator.isValid(request.getPassword())) {

            throw new WeakPasswordException(
                    "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number and one special character."
            );
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(request.getRole())
                .enabled(true)
                .build();

        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id ));
        return UserMapper.toResponse(user);
    }

    @Transactional
    public void blockUser(
            String username
    ) {

        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String admin = auth.getName();

        user.setEnabled(false);

        refreshTokenService.deleteByUser(user);

        auditService.save(
                admin,
                "BLOCK",
                user.getUsername(),
                "Blocked by administrator"
        );

        userRepository.save(user);
    }

    @Transactional
    public void unblockUser(
            String username
    ) {

        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String admin = auth.getName();

        user.setEnabled(true);

        auditService.save(
                admin,
                "UNBLOCK",
                user.getUsername(),
                "Unblocked by administrator"
        );

        userRepository.save(user);
    }

    @Transactional
    public void increaseFailedAttempts(
            User user
    ) {

        int attempts =
                user.getFailedLoginAttempts() + 1;

        user.setFailedLoginAttempts(
                attempts
        );

        if (attempts >=
                SecurityConstants
                        .MAX_FAILED_ATTEMPTS) {

            user.setEnabled(false);

            user.setLockedAt(
                    LocalDateTime.now()
            );

            refreshTokenService
                    .deleteByUser(user);


            auditService.save(
                    "SYSTEM",
                    "AUTO_BLOCK",
                    user.getUsername(),
                    "Too many failed login attempts"
            );
        }

        userRepository.save(user);
    }

    @Transactional
    public void resetFailedAttempts(
            User user
    ) {

        user.setFailedLoginAttempts(0);

        userRepository.save(user);
    }

    public boolean unlockWhenLockExpired(
            User user
    ) {

        if (user.getEnabled()) {
            return true;
        }

        if (user.getLockedAt() == null) {
            return false;
        }

        LocalDateTime unlockTime =
                user.getLockedAt()
                        .plusMinutes(
                                SecurityConstants
                                        .LOCK_TIME_MINUTES
                        );

        if (LocalDateTime.now().isAfter(unlockTime)) {

            user.setEnabled(true);

            user.setFailedLoginAttempts(0);

            user.setLockedAt(null);

            auditService.save(
                    "SYSTEM",
                    "AUTO_UNBLOCK",
                    user.getUsername(),
                    "Automatic unlock after lock period"
            );

            userRepository.save(user);

            return true;
        }

        return false;
    }

    @Transactional
    public void changePassword(
            String username,
            String currentPassword,
            String newPassword
    ) {

        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        boolean matches =
                passwordEncoder.matches(
                        currentPassword,
                        user.getPassword()
                );

        if (!matches) {

            throw new IllegalArgumentException(
                    "Current password is incorrect"
            );
        }

        // Validar la nueva contraseña antes de guardarla
        if (!PasswordPolicyValidator.isValid(newPassword)) {

            throw new WeakPasswordException(
                    "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number and one special character."
            );
        }

        user.setPassword(
                passwordEncoder.encode(
                        newPassword
                )
        );

        refreshTokenService.deleteByUser(user);

        auditService.save(
                username,
                "CHANGE_PASSWORD",
                username,
                "Password changed"
        );

        userRepository.save(user);
    }

}