package com.i2i.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.i2i.ems.dto.AddressDto;
import com.i2i.ems.helper.CustomException;
import com.i2i.ems.service.AddressService;

/**
 * Controller to handle Address related requests.
 */
@RestController
@RequestMapping("v1/employees/{employeeId}/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * <p>
     * Adds a new address by collecting details from user input.
     * </p>
     *
     * @param employeeId and addressDto to add the address.
     * @return the newly created Address object and the HttpStatus.
     * @throws CustomException when exception is thrown.
     */
    @PostMapping
    public ResponseEntity<AddressDto> addAddress(@Validated @PathVariable int employeeId,
                                                 @RequestBody AddressDto addressDto)
            throws CustomException {
        AddressDto createdAddress = addressService.addAddress(addressDto);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    /**
     * <p>
     * Prompts the user to enter new values for the address to update the Address.
     * </p>
     *
     * @param employeeId and the addressDto to update the address.
     * @return the updated Address Object and the HttpStatus.
     * @throws CustomException when exception is thrown.
     */
    @PutMapping
    public ResponseEntity<AddressDto> updateAddress(@Validated @PathVariable int employeeId,
                                                    @RequestBody AddressDto addressDto)
            throws CustomException {
        AddressDto updatedAddress = addressService.updateAddress(employeeId, addressDto);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    /**
     * <p>
     * Retrieves and displays the details of an address.
     * </p>
     *
     * @param addressId to retrieve the address.
     * @return the address object and the HttpStatus code.
     * @throws CustomException, if occurred.
     */
    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable int addressId)
            throws CustomException {
        return new ResponseEntity<>(addressService.getAddressById(addressId), HttpStatus.OK);
    }

    /**
     * <p>
     * Deletes an Address based on the provided employee ID.
     * </p>
     *
     * @param employeeId for deleting the address.
     * @return the HttpStatus code when deleted
     * @throws CustomException, if occurred.
     */
    @DeleteMapping
    public ResponseEntity<HttpStatus> removeAddress(@PathVariable int employeeId)
            throws CustomException {
        addressService.deleteAddress(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}



