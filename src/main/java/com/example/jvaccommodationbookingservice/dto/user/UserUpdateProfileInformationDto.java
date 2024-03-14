package com.example.jvaccommodationbookingservice.dto.user;

import com.example.jvaccommodationbookingservice.validation.Password;
import jakarta.validation.constraints.Email;
public record UserUpdateProfileInformationDto(
        @Email
        String email,
        @Password
        String password,
        String firstName,
        String lastName
) {
}
