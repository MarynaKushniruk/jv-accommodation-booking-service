package com.example.jvaccommodationbookingservice.mapper;

import com.example.jvaccommodationbookingservice.config.MapperConfig;
import com.example.jvaccommodationbookingservice.dto.accommodationDto.AccommodationFullInfoResponseDto;
import com.example.jvaccommodationbookingservice.dto.accommodationDto.AccommodationIncompleteInfoResponseDto;
import com.example.jvaccommodationbookingservice.dto.accommodationDto.AccommodationRequestDto;
import com.example.jvaccommodationbookingservice.model.Accommodation;
import com.example.jvaccommodationbookingservice.model.Address;
import com.example.jvaccommodationbookingservice.model.Amenity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class)
public interface AccommodationMapper {
    @Mapping(target = "address", source = "address", qualifiedByName = "getAddressString")
    @Mapping(target = "amenities", source = "amenities", qualifiedByName = "setAmenitiesToString")
    AccommodationFullInfoResponseDto toFullDto(Accommodation accommodation);

    @Mapping(target = "address", source = "address", qualifiedByName = "getAddressString")
    AccommodationIncompleteInfoResponseDto toIncompleteDto(Accommodation accommodation);

    @Mapping(target = "type", ignore = true)
    @Mapping(target = "size", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Accommodation toEntity(AccommodationRequestDto request);

    @Named("setAmenitiesToString")
    default Set<String> setAmenitiesToString(Set<Amenity> amenities) {
        return amenities.stream()
                .map(Amenity::getName)
                .collect(Collectors.toSet());
    }

    @Named("getAddressString")
    default String getAddressString(Address address) {
        return address.getAddress();
    }

}
