package com.newproject.dreamshops.Controllers;

import com.newproject.dreamshops.DTO.UserDto;
import com.newproject.dreamshops.Entity.User;
import com.newproject.dreamshops.Response.ApiResponse;
import com.newproject.dreamshops.Service.User.IUserService;

import com.newproject.dreamshops.request.CreateUserRequest;
import com.newproject.dreamshops.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        UserDto userDto = userService.mapUserToDto(user);
        return ResponseEntity.ok(new ApiResponse("success", userDto));

    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
        User user = userService.createUser(request);
        UserDto userDto = userService.mapUserToDto(user);// Service layer handles exception
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("User created successfully!", userDto));
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId) {
        User user = userService.updateUser(request, userId);
        UserDto userDto = userService.mapUserToDto(user);
        return ResponseEntity.ok(new ApiResponse("update user successfully!", userDto));

    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse("User deleted successfully!", null));
    }
}

