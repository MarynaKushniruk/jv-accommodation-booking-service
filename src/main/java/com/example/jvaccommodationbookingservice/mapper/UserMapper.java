package com.example.jvaccommodationbookingservice.mapper;

import com.example.jvaccommodationbookingservice.config.MapperConfig;
import com.example.jvaccommodationbookingservice.dto.userDto.UserRegistrationRequestDto;
import com.example.jvaccommodationbookingservice.dto.userDto.UserResponseDto;
import com.example.jvaccommodationbookingservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    @Mapping(target = "role", expression = "java(User.Role.ROLE_CUSTOMER)")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toEntity(UserRegistrationRequestDto request);
}