package com.example.jvaccommodationbookingservice.controller;

import com.example.jvaccommodationbookingservice.dto.userDto.UserLoginRequestDto;
import com.example.jvaccommodationbookingservice.dto.userDto.UserLoginResponseDto;
import com.example.jvaccommodationbookingservice.dto.userDto.UserRegistrationRequestDto;
import com.example.jvaccommodationbookingservice.dto.userDto.UserResponseDto;
import com.example.jvaccommodationbookingservice.exception.RegistrationException;
import com.example.jvaccommodationbookingservice.service.userservice.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationController.class);
    private final UserService userService;

    @PostMapping(value = "/register", consumes = APPLICATION_JSON_VALUE)

    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto request)
            throws RegistrationException {
        LOGGER.info("Received registration request for user with email: {}", request.getEmail());
        return userService.register(request);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto requestDto) {
        return userService.authenticate(requestDto);
    }
}
