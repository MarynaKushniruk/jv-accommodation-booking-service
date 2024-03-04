package com.example.jvaccommodationbookingservice.service.paymentservice;

import com.example.jvaccommodationbookingservice.dto.paymentDto.PaymentCanceledResponseDto;
import com.example.jvaccommodationbookingservice.dto.paymentDto.PaymentCreateRequestDto;
import com.example.jvaccommodationbookingservice.dto.paymentDto.PaymentResponseDto;
import com.example.jvaccommodationbookingservice.dto.paymentDto.PaymentSuccessResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentService {
    List<PaymentResponseDto> getAllPaymentByUserId(Long userId, Pageable pageable);

    String initializeSession(PaymentCreateRequestDto request);

    PaymentSuccessResponseDto confirmPaymentIntent();

    PaymentCanceledResponseDto paymentCancellation();
}
