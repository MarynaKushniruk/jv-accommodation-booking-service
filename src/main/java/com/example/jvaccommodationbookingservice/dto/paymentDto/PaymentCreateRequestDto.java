package com.example.jvaccommodationbookingservice.dto.paymentDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentCreateRequestDto {
    private String productName;
    private Long bookingId;
    @Builder.Default
    private String paymentCardToken = "tok_visa";
}
