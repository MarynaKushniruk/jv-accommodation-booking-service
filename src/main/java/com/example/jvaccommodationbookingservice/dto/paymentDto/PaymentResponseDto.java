package com.example.jvaccommodationbookingservice.dto.paymentDto;

import java.math.BigDecimal;

public record PaymentResponseDto(
        Long id,
        String status,
        Long bookingId,
        BigDecimal amountToPay,
        String currency,
        String paymentMethodType

) {

}
