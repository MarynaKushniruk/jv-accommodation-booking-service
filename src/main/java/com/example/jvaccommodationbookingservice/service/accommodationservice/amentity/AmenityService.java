package com.example.jvaccommodationbookingservice.service.accommodationservice.amentity;

import com.example.jvaccommodationbookingservice.model.Amenity;

import java.util.Set;

public interface AmenityService {
    Amenity createAmenity(String amenityName);

    Amenity getAmenityByName(String amenityName);

    Set<Amenity> getSetAmenitiesByAmenitiesNames(Set<String> amenityNames);
}
