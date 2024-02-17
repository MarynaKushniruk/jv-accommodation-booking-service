package com.example.jvaccommodationbookingservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class AccommodationResponseDto {
    private Long id;
    private String accommodationType;
    private String address;
    private String size;
    private List<String> amenities = new ArrayList<>();
    private BigDecimal dailyRate;
    private Integer numberOfAvailable;
}
