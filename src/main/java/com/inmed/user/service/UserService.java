package com.inmed.user.service;

import com.inmed.auth.service.RefreshTokenService;
import com.inmed.exception.custom.DuplicateResourceException;
import com.inmed.exception.custom.ResourceNotFoundException;
import com.inmed.user.dto.CreateUserRequest;
import com.inmed.user.dto.UserResponse;
import com.inmed.user.entity.User;
import com.inmed.user.mapper.UserMapper;
import com.inmed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.inmed.user.dto.UserStatusRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {

            throw new DuplicateResourceException(
                    "Username already exists"
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

        user.setEnabled(false);

        refreshTokenService.deleteByUser(user);

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

        user.setEnabled(true);

        userRepository.save(user);
    }
}

