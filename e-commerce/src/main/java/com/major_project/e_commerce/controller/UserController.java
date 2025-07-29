package com.major_project.e_commerce.controller;

import com.major_project.e_commerce.dto.request.CreateUserRequest;
import com.major_project.e_commerce.dto.request.UpdateUserRequest;
import com.major_project.e_commerce.dto.response.UserResponse;
import com.major_project.e_commerce.response.ApiResponse;
import com.major_project.e_commerce.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        UserResponse userResponse = userService.getUserById(userId);
        return ResponseEntity.ok(ApiResponse.builder()
                        .message("User having id : " + userId)
                        .httpStatus(HttpStatus.OK.value())
                        .data(userResponse)
                .build());
    }

    @PostMapping("user/create")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
        UserResponse userResponse = userService.createUser(request);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("User created successfully!!")
                .httpStatus(HttpStatus.OK.value())
                .data(userResponse)
                .build());
    }

    @PutMapping("user/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request, @RequestParam Long userId) {
        UserResponse userResponse = userService.updateUser(request,userId);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("User updated successfully!!")
                .httpStatus(HttpStatus.OK.value())
                .data(userResponse)
                .build());
    }

    @DeleteMapping("user/delete")
    public ResponseEntity<ApiResponse> deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("User deleted successfully!!")
                .httpStatus(HttpStatus.OK.value())
                .build());
    }
}
