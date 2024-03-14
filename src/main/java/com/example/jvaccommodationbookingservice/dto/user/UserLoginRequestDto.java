package com.example.jvaccommodationbookingservice.dto.user;

import com.example.jvaccommodationbookingservice.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@FieldMatch(first = "password", second = "repeatPassword", message = "Passwords must match")
public class UserLoginRequestDto {
    @Email
    @NotEmpty
    @Size(min = 8, max = 20)
    private String email;
    @NotEmpty
    @Size(min = 8, max = 20)
    private String password;
}
