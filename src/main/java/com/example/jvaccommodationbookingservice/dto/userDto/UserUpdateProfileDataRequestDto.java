package com.example.jvaccommodationbookingservice.dto.userDto;

import lombok.Data;

@Data
public class UserUpdateProfileData {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
