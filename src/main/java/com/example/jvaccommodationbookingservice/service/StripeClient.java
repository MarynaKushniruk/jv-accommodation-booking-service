package com.example.jvaccommodationbookingservice.service;

import com.example.jvaccommodationbookingservice.dto.ChargeRequestDto;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StripeClient {

    StripeClient() {
        Stripe.apiKey = System.getenv().get("STRIPE_API_KEY");
    }

    public Charge charge(ChargeRequestDto chargeRequest)
            throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("source", chargeRequest.getStripeToken());
        return Charge.create(chargeParams);
    }
}
