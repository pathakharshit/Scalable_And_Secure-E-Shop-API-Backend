package com.major_project.e_commerce.service.user;

import com.major_project.e_commerce.dto.request.CreateUserRequest;
import com.major_project.e_commerce.dto.request.UpdateUserRequest;
import com.major_project.e_commerce.dto.response.UserResponse;
import com.major_project.e_commerce.model.User;

public interface UserService {
    UserResponse getUserById(Long userId);
    UserResponse createUser(CreateUserRequest request);
    UserResponse updateUser(UpdateUserRequest request,Long userId);
    User getAuthenticated();
    void deleteUser(Long userId);

    UserResponse convertToDto(User user);
}
