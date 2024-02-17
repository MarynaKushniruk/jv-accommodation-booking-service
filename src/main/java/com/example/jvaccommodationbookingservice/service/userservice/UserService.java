package com.example.jvaccommodationbookingservice.service.userservice;

import com.example.jvaccommodationbookingservice.dto.userDto.*;
import com.example.jvaccommodationbookingservice.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;
    UserLoginResponseDto authenticate(UserLoginRequestDto requestDto);
    UserResponseDto getProfileData(String email);
    UserResponseDto updateProfileData(String email, UserUpdateProfileDataRequestDto requestDto);
}
