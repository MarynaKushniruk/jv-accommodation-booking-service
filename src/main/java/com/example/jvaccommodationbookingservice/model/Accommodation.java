package com.example.jvaccommodationbookingservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@SQLDelete(sql = "UPDATE accommodations SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
@Table(name = "accommodations")
@Accessors(chain = true)
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private  AccommodationType type;
    @NotNull
    private String address;
    private String size;
    @Transient
    private List<String> amenities = new ArrayList<>();
    @NotNull
    private BigDecimal dailyRate;
    private Integer numberOfAvailable;
    @Column(name = "is_deleted")
    private boolean isDeleted;
}
