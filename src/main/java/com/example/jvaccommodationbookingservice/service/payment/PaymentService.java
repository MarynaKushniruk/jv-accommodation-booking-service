package com.example.jvaccommodationbookingservice.service.payment;

import com.example.jvaccommodationbookingservice.dto.payment.PaymentCanceledResponseDto;
import com.example.jvaccommodationbookingservice.dto.payment.PaymentCreateRequestDto;
import com.example.jvaccommodationbookingservice.dto.payment.PaymentResponseDto;
import com.example.jvaccommodationbookingservice.dto.payment.PaymentSuccessResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentService {
    List<PaymentResponseDto> getAllPaymentByUserId(Long userId, Pageable pageable);

    String initializeSession(PaymentCreateRequestDto request);

    PaymentSuccessResponseDto confirmPaymentIntent();

    PaymentCanceledResponseDto paymentCancellation();
}
