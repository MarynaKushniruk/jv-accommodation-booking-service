package com.example.jvaccommodationbookingservice.controller;

import com.example.jvaccommodationbookingservice.dto.userDto.UserResponseDto;
import com.example.jvaccommodationbookingservice.dto.userDto.UserUpdateProfileDataRequestDto;
import com.example.jvaccommodationbookingservice.service.userservice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Get profile information about user ",
            description = "Enables users to his profile information")
    UserResponseDto getUserData(Authentication authentication) {
        return userService.getProfileData(authentication.getName());
    }

    @PutMapping("/me")
    @Operation(summary = "Update profile information about user by user",
            description = "Enables users to update his profile information")
    UserResponseDto updateUserData(Authentication authentication,
                                   UserUpdateProfileDataRequestDto requestDto) {
        return userService.updateProfileData(authentication.getName(),requestDto);
    }
}
