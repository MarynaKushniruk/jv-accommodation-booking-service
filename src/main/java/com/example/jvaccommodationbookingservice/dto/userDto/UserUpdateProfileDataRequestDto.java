package com.example.jvaccommodationbookingservice.dto.userDto;

import lombok.Data;

@Data
public class UserUpdateProfileDataRequestDto {
    private String email;
    private String firstName;
    private String lastName;
}
