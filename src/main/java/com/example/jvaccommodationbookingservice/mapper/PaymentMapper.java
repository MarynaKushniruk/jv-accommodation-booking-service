package com.example.jvaccommodationbookingservice.mapper;

import com.example.jvaccommodationbookingservice.config.MapperConfig;
import com.example.jvaccommodationbookingservice.dto.payment.PaymentResponseDto;
import com.example.jvaccommodationbookingservice.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface PaymentMapper {
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "paymentMethodType", source = "paymentMethodType")
    PaymentResponseDto toDto(Payment payment, String paymentMethodType, String currency);
}
