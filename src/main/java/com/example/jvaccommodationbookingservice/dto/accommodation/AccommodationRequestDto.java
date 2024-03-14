package com.example.jvaccommodationbookingservice.dto.accommodation;

import com.example.jvaccommodationbookingservice.validation.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class AccommodationRequestDto {
    @NotBlank
    private String type;
    @NotBlank
    @Address
    private String address;
    @NotBlank
    private String size;
    @NotEmpty
    private Set<String> amenities;
    @NotNull
    @Positive
    private BigDecimal dailyRate;
    @NotNull
    @Positive
    private Integer availability;
}
