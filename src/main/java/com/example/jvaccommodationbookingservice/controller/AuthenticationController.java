package com.example.jvaccommodationbookingservice.controller;

import com.example.jvaccommodationbookingservice.dto.user.UserLoginRequestDto;
import com.example.jvaccommodationbookingservice.dto.user.UserLoginResponseDto;
import com.example.jvaccommodationbookingservice.dto.user.UserRegistrationRequestDto;
import com.example.jvaccommodationbookingservice.dto.user.UserResponseDto;
import com.example.jvaccommodationbookingservice.exception.RegistrationException;
import com.example.jvaccommodationbookingservice.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationController.class);
    private final UserService userService;

    @PostMapping(value = "/register", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Register user", description = "Register user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto request)
            throws RegistrationException {
        LOGGER.info("Received registration request for user with email: {}", request.getEmail());
        return userService.register(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Login user")
    @ResponseStatus(HttpStatus.OK)
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto requestDto) {
        return userService.authenticate(requestDto);
    }
}
