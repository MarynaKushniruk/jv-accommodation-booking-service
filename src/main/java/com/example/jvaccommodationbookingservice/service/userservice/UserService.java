package com.example.jvaccommodationbookingservice.service.userservice;

import com.example.jvaccommodationbookingservice.dto.userDto.UserUpdateProfileInformationDto;
import com.example.jvaccommodationbookingservice.dto.userDto.UserUpdateRoleDto;
import com.example.jvaccommodationbookingservice.dto.userDto.*;
import com.example.jvaccommodationbookingservice.exception.RegistrationException;
import com.example.jvaccommodationbookingservice.model.User;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;
    UserLoginResponseDto authenticate(UserLoginRequestDto requestDto);
    User getById(Long id);
    User getAuthenticated();
    boolean existsById(Long id);
    User getByEmail(String email);
    UserResponseDto getUserProfile();
    void updateUserRole(Long id, UserUpdateRoleDto updateRoleDto);

    UserResponseDto updateUserProfile(UserUpdateProfileInformationDto request);
}
