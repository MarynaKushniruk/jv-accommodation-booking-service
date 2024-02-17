package com.example.jvaccommodationbookingservice.dto;

import lombok.Data;

@Data
public class ChargeRequestDto {
    public enum Currency {
        EUR, USD;
    }
    private int amount;
    private Currency currency;
    private Long bookingId;
    private String stripeToken;
}
