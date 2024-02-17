package com.example.jvaccommodationbookingservice.controller;

import com.example.jvaccommodationbookingservice.dto.ChargeRequestDto;
import com.example.jvaccommodationbookingservice.service.StripeClient;
import com.stripe.model.Charge;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final StripeClient stripeClient;
    @PostMapping("/charge")
    public Charge chargeCard(@RequestBody ChargeRequestDto requestDto) throws Exception {
        requestDto.setCurrency(ChargeRequestDto.Currency.USD);
        Charge charge = stripeClient.charge(requestDto);
        return charge;
    }

}
