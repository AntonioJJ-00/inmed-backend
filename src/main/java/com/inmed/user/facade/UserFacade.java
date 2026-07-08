package com.inmed.user.facade;

import com.inmed.user.dto.*;
import com.inmed.user.service.command.UserCommandService;
import com.inmed.user.service.query.UserQueryService;
import com.inmed.user.service.security.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserCommandService commandService;
    private final UserQueryService queryService;
    private final UserSecurityService securityService;

    public UserResponse createUser(CreateUserRequest request) {
        return commandService.createUser(request);
    }

    public List<UserResponse> getAllUsers() {
        return queryService.getAllUsers();
    }

    public UserResponse getUserById(Long id) {
        return queryService.getUserById(id);
    }

    public void blockUser(String username) {
        securityService.blockUser(username);
    }

    public void unblockUser(String username) {
        securityService.unblockUser(username);
    }

    public void changePassword(String username, String current, String newPass) {
        securityService.changePassword(username, current, newPass);
    }

    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        return commandService.updateUser(id, request);
    }

    public void deleteUser(Long id) {
        commandService.deleteUser(id);
    }
}