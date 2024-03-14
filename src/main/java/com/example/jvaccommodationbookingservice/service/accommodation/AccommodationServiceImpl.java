package com.example.jvaccommodationbookingservice.service.accommodation;

import com.example.jvaccommodationbookingservice.dto.accommodation.AccommodationFullInfoResponseDto;
import com.example.jvaccommodationbookingservice.dto.accommodation.AccommodationIncompleteInfoResponseDto;
import com.example.jvaccommodationbookingservice.dto.accommodation.AccommodationRequestDto;
import com.example.jvaccommodationbookingservice.dto.accommodation.AccommodationUpdateRequestDto;
import com.example.jvaccommodationbookingservice.exception.DataProcessingException;
import com.example.jvaccommodationbookingservice.exception.EntityNotFoundException;
import com.example.jvaccommodationbookingservice.mapper.AccommodationMapper;
import com.example.jvaccommodationbookingservice.model.Accommodation;
import com.example.jvaccommodationbookingservice.model.Accommodation.Size;
import com.example.jvaccommodationbookingservice.model.Accommodation.Type;
import com.example.jvaccommodationbookingservice.model.Address;
import com.example.jvaccommodationbookingservice.model.Amenity;
import com.example.jvaccommodationbookingservice.repository.AccommodationRepository;
import com.example.jvaccommodationbookingservice.service.accommodation.address.AddressService;
import com.example.jvaccommodationbookingservice.service.accommodation.amentity.AmenityService;
import com.example.jvaccommodationbookingservice.telegram.BookingBot;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {
    private static final String[] HOUSE_TYPES_WITH_ONE_ADDRESS_AND_AVAILABILITY =
            new String[]{"HOUSE", "VACATION_HOME", "COTTAGE", "TOWNHOUSE"};

    private final AccommodationRepository accommodationRepository;
    private final AccommodationMapper accommodationMapper;
    private final AmenityService amenityService;
    private final AddressService addressService;
    private final BookingBot bookingBot;

    @Override
    @Transactional
    public AccommodationFullInfoResponseDto add(AccommodationRequestDto accommodation) {
        Type type = checkAndGetSizeOrType(accommodation.getType(), Type.class);
        checkAvailabilityForHouseType(type, accommodation.getAvailability());
        checkValidAddressForHouseType(type, accommodation.getAddress());
        Size size = checkAndGetSizeOrType(accommodation.getSize(), Size.class);
        Accommodation accommodationEntity = accommodationMapper.toEntity(accommodation);
        accommodationEntity.setAmenities(amenityService.getSetAmenitiesByAmenitiesNames(
                accommodation.getAmenities())
        );
        accommodationEntity.setAddress(
                addressService.getAddressIfExistingOrSaveAndGet(accommodation.getAddress())
        );
        accommodationEntity.setType(type);
        accommodationEntity.setSize(size);

        Accommodation checkedAccommodation = checkAndReturnExistingAccommodation(accommodationEntity);

        if (checkedAccommodation != null) {
            checkedAccommodation.setAvailability(
                    checkedAccommodation.getAvailability() + accommodation.getAvailability()
            );
            bookingBot.handleIncomingMessage("Update accommodation availability |"
                    + System.lineSeparator()
                    + accommodationMapper.toFullDto(checkedAccommodation).toString()
            );
        } else {
            accommodationEntity = accommodationRepository.save(accommodationEntity);
            bookingBot.handleIncomingMessage("Created new accommodation |"
                    + System.lineSeparator()
                    + accommodationMapper.toFullDto(accommodationEntity).toString()
            );
        }

        return checkedAccommodation != null
                ? accommodationMapper.toFullDto(checkedAccommodation)
                : accommodationMapper.toFullDto(accommodationEntity);

    }

    @Override
    public List<AccommodationIncompleteInfoResponseDto> findAll(Pageable pageable) {
        return accommodationRepository
                .findAll(pageable)
                .stream()
                .filter(accommodation -> accommodation.getAvailability() > 0)
                .map(accommodationMapper::toIncompleteDto)
                .toList();
    }

    @Override
    public AccommodationFullInfoResponseDto getById(Long accommodationId) {
        return accommodationMapper.toFullDto(getAccommodationById(accommodationId));
    }

    @Override
    public void update(AccommodationUpdateRequestDto requestDto, Long id) {
        Accommodation accommodation = getAccommodationById(id);
        if (requestDto.getAmenities() != null && !requestDto.getAmenities().isEmpty()) {
            Set<Amenity> amenities = amenityService.getSetAmenitiesByAmenitiesNames(
                    requestDto.getAmenities()
            );
            accommodation.setAmenities(amenities);
        }

        if (requestDto.getAvailability() != null) {
            checkAvailabilityForHouseType(accommodation.getType(), requestDto.getAvailability());
            accommodation.setAvailability(requestDto.getAvailability());
        }

        if (requestDto.getDailyRate() != null) {
            accommodation.setDailyRate(requestDto.getDailyRate());
        }
        accommodationRepository.save(accommodation);
    }

    @Override
    public void deleteById(Long id) {

        Accommodation accommodation = accommodationRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find accommodation by id: " + id)
        );
        checkAddressForHouseTypeAndDelete(accommodation.getType(), accommodation.getAddress());
        accommodationRepository.deleteById(id);
    }

    @Override
    public Accommodation getAccommodationById(final Long id) {
        return accommodationRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find accommodation by id: " + id)
        );
    }

    private void checkAddressForHouseTypeAndDelete(Type type, Address address) {
        for (String value : HOUSE_TYPES_WITH_ONE_ADDRESS_AND_AVAILABILITY) {
            if (type.name().equals(value)) {
                addressService.deleteById(address.getId());
            }
        }
    }

    private Accommodation checkAndReturnExistingAccommodation(Accommodation accommodation) {
        return accommodationRepository.findByTypeAndAddressIdAndSizeAndDailyRate(
                accommodation.getType(),
                accommodation.getAddress().getId(),
                accommodation.getSize(),
                accommodation.getDailyRate()
        ).orElse(null);
    }

    private void checkAvailabilityForHouseType(Type type, Integer availability) {
        for (String value : HOUSE_TYPES_WITH_ONE_ADDRESS_AND_AVAILABILITY) {
            if (type.name().equals(value) && availability > 1) {
                throw new DataProcessingException(
                        "There cannot be more than 1 availability for this type: " + type.name()
                                + " of accommodation"
                );
            }
        }
    }

    private void checkValidAddressForHouseType(Type type, String address) {
        if (!addressService.checkExistingAddress(address)) {
            return;
        }

        if (checkContainsTypeFromHouseTypes(type)) {
            throw new DataProcessingException("This address: " + address
                    + " already exists for another property of this type: " + type.name());
        }

        Address addressFromDb = addressService.getAddressByAddressArgument(address);
        List<Accommodation> accommodations = accommodationRepository.findByAddress(addressFromDb);

        for (Accommodation accommodation : accommodations) {
            if (checkContainsTypeFromHouseTypes(accommodation.getType())) {
                throw new DataProcessingException("This address: " + address
                        + " already exists for another property of this type: "
                        + accommodation.getType().name());
            }
        }
    }

    private boolean checkContainsTypeFromHouseTypes(Type type) {
        return Arrays.stream(
                HOUSE_TYPES_WITH_ONE_ADDRESS_AND_AVAILABILITY).toList().contains(type.name()
        );
    }

    private <T extends Enum<T>> T checkAndGetSizeOrType(String name, Class<T> enumType) {
        String validName = name.trim().contains(" ")
                ? name.toUpperCase().trim().replace(" ", "_")
                : name.toUpperCase().trim();

        for (T value : enumType.getEnumConstants()) {
            if (validName.equals(value.name())) {
                return value;
            }
        }

        throw new DataProcessingException(generateExceptionMessage(enumType));
    }

    private <T extends Enum<T>> String generateExceptionMessage(Class<T> enumType) {
        T[] enumConstants = enumType.getEnumConstants();
        StringBuilder builder =
                new StringBuilder("The entered data is incorrect, select one of these: ");

        for (int i = 0; i < enumConstants.length; i++) {
            builder.append(enumConstants[i].name());

            if (i < enumConstants.length - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }
}

