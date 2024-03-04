package com.example.jvaccommodationbookingservice.dto.accommodationDto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class AccommodationUpdateRequestDto {
    private Set<String> amenities;
    @Positive
    private BigDecimal dailyRate;
    @Positive
    private Integer availability;
}
