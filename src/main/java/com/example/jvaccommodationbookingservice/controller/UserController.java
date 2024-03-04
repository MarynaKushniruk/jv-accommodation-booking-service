package com.example.jvaccommodationbookingservice.controller;

import com.example.jvaccommodationbookingservice.dto.userDto.UserUpdateProfileInformationDto;
import com.example.jvaccommodationbookingservice.dto.userDto.UserUpdateRoleDto;
import com.example.jvaccommodationbookingservice.dto.userDto.UserResponseDto;
import com.example.jvaccommodationbookingservice.service.userservice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User management", description = "Endpoints for users actions")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    @Operation(summary = "Get user profile", description = "Get user profile")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getUserProfile() {
        return userService.getUserProfile();
    }

    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "Update user role", description = "Update user role by user id")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserRole(
            @PathVariable Long id, @RequestBody UserUpdateRoleDto updateRoleDto
    ) {
        userService.updateUserRole(id, updateRoleDto);
    }

    @PutMapping ("/me")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    @Operation(summary = "Update user profile", description = "Update user profile")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto updateUserProfile(@RequestBody UserUpdateProfileInformationDto request) {
        return userService.updateUserProfile(request);
    }

}