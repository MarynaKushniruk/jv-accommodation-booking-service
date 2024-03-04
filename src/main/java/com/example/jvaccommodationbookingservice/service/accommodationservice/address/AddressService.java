package com.example.jvaccommodationbookingservice.service.accommodationservice.address;

import com.example.jvaccommodationbookingservice.model.Address;

public interface AddressService {
    Address createAddress(String address);

    Address getAddressByAddressArgument(String address);

    Address getAddressIfExistingOrSaveAndGet(String address);

    boolean checkExistingAddress(String address);

    void deleteById(Long id);
}
