package com.example.jvaccommodationbookingservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.net.URL;


@Entity
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE payments SET is_deleted = TRUE WHERE id = ?")
@Where(clause = "is_deleted = FALSE")
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @JoinColumn(name = "booking_id")
    private Long bookingId;

    @Column(name = "session_url")
    private URL sessionUrl;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "amount_to_pay")
    private BigDecimal amountToPay;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public enum Status {
        PENDING,
        PAID,
        CANCELED,
        FAILED
    }
}