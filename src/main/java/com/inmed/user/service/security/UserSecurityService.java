package com.inmed.user.service.security;

import com.inmed.audit.service.AuditService;
import com.inmed.exception.custom.ResourceNotFoundException;
import com.inmed.security.SecurityConstants;
import com.inmed.user.entity.User;
import com.inmed.user.repository.UserRepository;
import com.inmed.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserSecurityService {

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuditService auditService;

    // =========================
    // 🔐 LOGIN SECURITY FLOW
    // =========================

    @Transactional
    public void increaseFailedAttempts(User user) {

        int attempts = user.getFailedLoginAttempts() + 1;
        user.setFailedLoginAttempts(attempts);

        if (attempts >= SecurityConstants.MAX_FAILED_ATTEMPTS) {

            user.setEnabled(false);
            user.setLockedAt(LocalDateTime.now());

            refreshTokenService.revokeAll(user);

            auditService.save(
                    "SYSTEM",
                    "AUTO_BLOCK",
                    user.getUsername(),
                    "Too many failed attempts"
            );
        }

        userRepository.save(user);
    }

    @Transactional
    public void resetFailedAttempts(User user) {

        user.setFailedLoginAttempts(0);
        userRepository.save(user);
    }

    @Transactional
    public boolean unlockWhenLockExpired(User user) {

        if (user.getEnabled()) return true;

        if (user.getLockedAt() == null) return false;

        LocalDateTime unlockTime =
                user.getLockedAt().plusMinutes(SecurityConstants.LOCK_TIME_MINUTES);

        if (LocalDateTime.now().isAfter(unlockTime)) {

            user.setEnabled(true);
            user.setFailedLoginAttempts(0);
            user.setLockedAt(null);

            auditService.save(
                    "SYSTEM",
                    "AUTO_UNBLOCK",
                    user.getUsername(),
                    "Auto unlock"
            );

            userRepository.save(user);
            return true;
        }

        return false;
    }

    // =========================
    // 🔐 ADMIN ACTIONS
    // =========================

    public void blockUser(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setEnabled(false);
        user.setLockedAt(LocalDateTime.now());

        refreshTokenService.revokeAll(user);

        auditService.save("SYSTEM", "BLOCK", username, "User blocked");

        userRepository.save(user);
    }

    public void unblockUser(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setEnabled(true);
        user.setFailedLoginAttempts(0);
        user.setLockedAt(null);

        auditService.save("SYSTEM", "UNBLOCK", username, "User unblocked");

        userRepository.save(user);
    }

    public void changePassword(String username, String current, String newPass) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(current, user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        user.setPassword(passwordEncoder.encode(newPass));
        refreshTokenService.revokeAll(user);

        auditService.save(username, "CHANGE_PASSWORD", username, "Password changed");

        userRepository.save(user);
    }
}