package com.i2i.ems.service;

import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.i2i.ems.dto.AddressDto;
import com.i2i.ems.helper.CustomException;
import com.i2i.ems.mapper.AddressMapper;
import com.i2i.ems.model.Address;
import com.i2i.ems.repository.AddressRepository;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    private static final Logger logger = LogManager.getLogger(AddressService.class);

    /**
     * <p>
     * Adds a new address by collecting details from user input.
     * </p>
     *
     * @param employeeId and addressDto to add the address.
     * @return the newly created Address object.
     * @throws CustomException when exception is thrown.
     */
    public AddressDto addAddress(int employeeId, AddressDto addressDto) throws CustomException {
        try {
            Address address = AddressMapper.dtoToModel(addressDto);
            address = addressRepository.save(address);
            AddressDto addressDto1 = AddressMapper.modelToDto(address);
            logger.info("Address added successfully with ID: {}",
                    addressDto1.getAddressId());
            return addressDto1;
        } catch (Exception e) {
            logger.error("Error adding an address", e);
            throw new CustomException("Server Error!!!!", e);
        }
    }

    /**
     * <p>
     *  updates the user with the new address.
     * </p>
     *
     * @param employeeId and the addressDto to update the address.
     * @return the updated Address Object.
     * @throws CustomException when exception is thrown.
     */
    public AddressDto updateAddress(int employeeId, AddressDto addressDto)
            throws CustomException {
        try {
            Address address = AddressMapper.dtoToModel((addressDto));
            addressRepository.save(address);
            logger.info("Address updated successfully for ID: {}",
                    addressDto.getAddressId());
            return AddressMapper.modelToDto((address));
        } catch (Exception e) {
            logger.error("Error updating an address with id : {}",
                    addressDto.getAddressId(), e);
            throw new CustomException("Server Error!!!!", e);
        }
    }

    public Address getAddressByEmployeeId(int id) throws CustomException {
        try {
            return addressRepository.findByAddressIdAndIsDeletedFalse(id);
        } catch (Exception e) {
            logger.error("Error retrieving an address with employee id : {} ", id, e);
            throw new CustomException("Server Error!!!!", e);
        }
    }

    /**
     * <p>
     *  Retrieves and displays the details of an address.
     * </p>
     *
     * @param id to retrieve the address.
     * @return the address object.
     * @throws NullPointerException, CustomException
     */
    public AddressDto getAddressById(int id) throws NullPointerException, CustomException {
        try {
            Address address = addressRepository.findByAddressIdAndIsDeletedFalse(id);
            logger.info("Retrieved address details for ID: {}", id);
            return AddressMapper.modelToDto(address);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("No Address found with id: " + id, e);
        } catch (Exception e) {
            logger.error("Error in retrieving an address with id : {} ", id, e);
            throw new CustomException("Server Error!!!!", e);
        }
    }

    /**
     * <p>
     * Deletes an Address based on the provided employee ID.
     * </p>
     *
     * @param id for deleting the address.
     * @throws NoSuchElementException, CustomException.
     */
    public void deleteAddress(int id) throws NoSuchElementException, CustomException {
        try {
            Address address = getAddressByEmployeeId(id);
            if (null != address) {
                address.setDeleted(true);
                addressRepository.save(address);
            }
            logger.info("Address removed successfully with ID: {}", id);
        } catch (NoSuchElementException e) {
            logger.error("Error in deleting an address with the id : {}", id, e);
            throw new NoSuchElementException("No Address found with id: " + id, e);
        } catch (Exception e) {
            logger.error("Error in deleting an address with the id : {}", id, e);
            throw new CustomException("Server Error!!!!", e);
        }
    }
}

