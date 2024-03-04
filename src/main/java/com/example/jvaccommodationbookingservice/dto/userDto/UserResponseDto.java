package com.example.jvaccommodationbookingservice.dto.userDto;

import com.example.jvaccommodationbookingservice.model.User;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
}
