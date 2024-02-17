package com.example.jvaccommodationbookingservice.mapper;

import com.example.jvaccommodationbookingservice.config.MapperConfig;
import com.example.jvaccommodationbookingservice.dto.userDto.UserResponseDto;
import com.example.jvaccommodationbookingservice.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);
}