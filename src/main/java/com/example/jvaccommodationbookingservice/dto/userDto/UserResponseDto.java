package com.example.jvaccommodationbookingservice.dto.userDto;

import com.example.jvaccommodationbookingservice.model.Role;
import lombok.Data;
import java.util.Set;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Set<Role> roles;
}
