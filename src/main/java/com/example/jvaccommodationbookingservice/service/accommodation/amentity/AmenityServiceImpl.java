package com.example.jvaccommodationbookingservice.service.accommodation.amentity;

import com.example.jvaccommodationbookingservice.exception.EntityNotFoundException;
import com.example.jvaccommodationbookingservice.model.Amenity;
import com.example.jvaccommodationbookingservice.repository.AmenityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AmenityServiceImpl implements AmenityService {
    private final AmenityRepository amenityRepository;

    @Override
    public Amenity createAmenity(final String amenityName) {
        Amenity amenity = new Amenity();
        amenity.setName(amenityName.toUpperCase());
        return amenityRepository.save(amenity);
    }

    @Override
    public Amenity getAmenityByName(final String amenityName) {
        return amenityRepository.findByName(amenityName).orElseThrow(
                () -> new EntityNotFoundException("Can't find amenity by name: " + amenityName)
        );
    }

    @Override
    public Set<Amenity> getSetAmenitiesByAmenitiesNames(final Set<String> amenityNames) {
        Set<Amenity> amenities = new HashSet<>();

        for (String name : amenityNames) {
            if (!amenityRepository.existsByName(name.toUpperCase())) {
                amenities.add(createAmenity(name));
            } else {
                amenities.add(getAmenityByName(name));
            }
        }
        return amenities;
    }
}
