package com.i2i.ems.mapper;

import com.i2i.ems.dto.AddressDto;
import com.i2i.ems.model.Address;

/**
 * AddressMapper is a class that provides methods to convert
 * between Address model objects and AddressDto data transfer objects.
 */
public class AddressMapper {

    /**
     * <p>
     * Converts an Address model to an AddressDto.
     * </p>
     *
     * @param address the Address model to be converted
     * @return an AddressDto representation of the given Address, or null if the input is null
     */
    public static AddressDto modelToDto(Address address) {
        return AddressDto.builder()
                .addressId(address.getAddressId())
                .area(address.getArea())
                .city(address.getCity())
                .country(address.getCountry())
                .build();
    }

    /**
     * <p>
     * Converts an AddressDto to an Address model.
     * </p>
     *
     * @param addressDto the AddressDto to be converted
     * @return an Address representation of the given AddressDto, or null if the input is null
     */
    public static Address dtoToModel(AddressDto addressDto) {
        return Address.builder()
                .addressId(addressDto.getAddressId())
                .area(addressDto.getArea())
                .city(addressDto.getCity())
                .country(addressDto.getCountry())
                .build();
    }
}
