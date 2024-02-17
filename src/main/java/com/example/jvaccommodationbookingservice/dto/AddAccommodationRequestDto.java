package com.example.jvaccommodationbookingservice.dto;

import com.example.jvaccommodationbookingservice.model.AccommodationType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class AddAccommodationRequestDto {
    @NotNull
    private AccommodationType type;
    @NotNull
    private String address;
    private String size;
    private List<String> amenities = new ArrayList<>();
    @NotNull
    private BigDecimal dailyRate;
    @NotNull
    private Integer numberOfAvailable;
}
