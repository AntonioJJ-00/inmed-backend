package com.inmed.user.service.query;

import com.inmed.exception.custom.ResourceNotFoundException;
import com.inmed.user.dto.UserResponse;
import com.inmed.user.mapper.UserMapper;
import com.inmed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}