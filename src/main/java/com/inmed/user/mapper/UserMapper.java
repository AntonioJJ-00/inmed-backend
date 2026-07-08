package com.inmed.user.mapper;

import com.inmed.user.dto.UserResponse;
import com.inmed.user.entity.User;

import java.util.List;

public class UserMapper {

    private UserMapper() {}

    public static UserResponse toResponse(User user) {

        if (user == null) return null;

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .role(user.getRole())
                .enabled(user.getEnabled())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static List<UserResponse> toResponseList(List<User> users) {
        return users.stream()
                .map(UserMapper::toResponse)
                .toList();
    }
}