package com.example.jvaccommodationbookingservice.controller;


import com.example.jvaccommodationbookingservice.dto.payment.PaymentCanceledResponseDto;
import com.example.jvaccommodationbookingservice.dto.payment.PaymentCreateRequestDto;
import com.example.jvaccommodationbookingservice.dto.payment.PaymentResponseDto;
import com.example.jvaccommodationbookingservice.dto.payment.PaymentSuccessResponseDto;
import com.example.jvaccommodationbookingservice.service.payment.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Payment management", description = "Endpoints for payments action")
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "Get all user payments", description = "Get user payments")
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentResponseDto> getPaymentByUser(
            @RequestParam(name = "user_id") Long userId, Pageable pageable
    ) {
        return paymentService.getAllPaymentByUserId(userId, pageable);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    @Operation(summary = "Initialize session",
            description = "Initialize session and create payment")
    @ResponseStatus(HttpStatus.CREATED)
    public String initializeSession(@RequestBody PaymentCreateRequestDto request) {
        return paymentService.initializeSession(request);
    }

    @GetMapping("/success")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    @Operation(summary = "Success payment", description = "Confirm payment intent")
    @ResponseStatus(HttpStatus.OK)
    public PaymentSuccessResponseDto successfulPaymentProcessing() {
        return paymentService.confirmPaymentIntent();
    }

    @GetMapping("/cancel")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    @Operation(summary = "Payment cancellation", description = "Payment cancellation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public PaymentCanceledResponseDto paymentCancellation() {
        return paymentService.paymentCancellation();
    }
}
