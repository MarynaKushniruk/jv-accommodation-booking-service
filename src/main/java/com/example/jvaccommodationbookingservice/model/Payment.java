package com.example.jvaccommodationbookingservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.net.URL;


@Entity
@Data
@SQLDelete(sql = "UPDATE payments SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
@Table(name = "payments")
@Accessors(chain = true)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private PaymentStatus status;
    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;
    private String sessionId;
    @NotNull
    private BigDecimal totalPrice;
    private URL sessionUrl;
}
