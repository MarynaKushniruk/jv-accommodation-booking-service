package com.example.jvaccommodationbookingservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Data
@SQLDelete(sql = "UPDATE bookings SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
@Table(name = "bookings")
@Accessors(chain = true)
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private LocalDateTime checkInTime;
    @NotNull
    private LocalDateTime checkOutTime;
    @ManyToOne
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private BookingStatus status;
    @Column(name = "is_deleted")
    private boolean isDeleted;
}
