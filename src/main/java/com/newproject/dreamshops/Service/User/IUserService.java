package com.newproject.dreamshops.Service.User;

import com.newproject.dreamshops.DTO.UserDto;
import com.newproject.dreamshops.Entity.User;
import com.newproject.dreamshops.request.CreateUserRequest;
import com.newproject.dreamshops.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);

    User createUser(CreateUserRequest request);

    User updateUser(UserUpdateRequest request, Long userId);

    void deleteUser(Long userId);

    UserDto mapUserToDto(User user);

    User getAuthenticatedUser();
}
