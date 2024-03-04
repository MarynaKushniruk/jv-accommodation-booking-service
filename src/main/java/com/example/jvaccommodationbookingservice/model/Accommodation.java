package com.example.jvaccommodationbookingservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.Set;

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
    @Column(unique = true, nullable = false)
    private Type type;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private Size size;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "accommodations_amenities",
            joinColumns = @JoinColumn(name = "accommodation_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id"))
    private Set<Amenity> amenities;;
    @Column(name = "daily_rate", nullable = false)
    private BigDecimal dailyRate;
    private Integer availability;;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public enum Type {
        HOUSE,
        APARTMENT,
        CONDO,
        VACATION_HOME,
        VACATION_APARTMENT,
        COTTAGE,
        TOWNHOUSE
    }

    public enum Size {
        STUDIO,
        ONE_BEDROOM,
        TWO_BEDROOM,
        THREE_BEDROOM,
        FOUR_BEDROOM
    }
}
