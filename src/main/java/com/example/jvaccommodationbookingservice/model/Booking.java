package com.example.jvaccommodationbookingservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE bookings SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
@Table(name = "bookings")
@Accessors(chain = true)
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "check_in_date", nullable = false)
    private LocalDateTime checkInDate;
    @Column(name = "check_out_date")
    private LocalDateTime checkOutDate;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", nullable = false, unique = true)
    private Accommodation accommodation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public enum Status {
        PENDING,
        CONFIRMED,
        CANCELED,
        EXPIRED,
        FAILED
    }
}
