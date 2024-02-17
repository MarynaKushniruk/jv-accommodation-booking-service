package com.example.jvaccommodationbookingservice.service;

import com.example.jvaccommodationbookingservice.dto.userDto.UserLoginRequestDto;
import com.example.jvaccommodationbookingservice.dto.userDto.UserLoginResponseDto;
import com.example.jvaccommodationbookingservice.dto.userDto.UserRegistrationRequestDto;
import com.example.jvaccommodationbookingservice.dto.userDto.UserResponseDto;
import com.example.jvaccommodationbookingservice.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;
    UserLoginResponseDto authenticate(UserLoginRequestDto requestDto);
}
