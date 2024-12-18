package com.i2i.ems.service;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.i2i.ems.dto.AddressDto;
import com.i2i.ems.helper.CustomException;
import com.i2i.ems.model.Address;
import com.i2i.ems.model.Employee;
import com.i2i.ems.repository.AddressRepository;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private AddressService addressService;

    private Employee employee;
    private Address address;
    private AddressDto addressDto;

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .addressId(1)
                .area("testArea")
                .city("testCity")
                .country("testCountry")
                .build();

        addressDto = AddressDto.builder()
                .addressId(1)
                .area("testArea")
                .city("testCity")
                .country("testCountry")
                .build();

        employee = Employee.builder()
                .id(1)
                .address(address)
                .build();
    }

    @Test
    void testAddAddress() throws CustomException {
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        AddressDto result = addressService.addAddress(addressDto);
        assertNotNull(result);
        assertEquals(addressDto.getArea(), result.getArea());
    }

    @Test
    void testAddAddressThrowsException() {
        when(addressRepository.save(any(Address.class))).thenThrow(RuntimeException.class);
        assertThrows(CustomException.class, () -> addressService.addAddress(addressDto));
    }

    @Test
    void testUpdateAddress() throws CustomException {
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        AddressDto result = addressService.updateAddress(anyInt(), addressDto);
        assertNotNull(result);
        assertEquals(addressDto.getArea(), result.getArea());
    }

    @Test
    void testUpdateAddressThrowsException() {
        when(addressRepository.save(any(Address.class))).thenThrow(RuntimeException.class);
        assertThrows(CustomException.class, () -> addressService.updateAddress(anyInt(), addressDto));
    }

    @Test
    void testGetAddressByEmployeeId() throws CustomException {
        when(employeeService.getEmployeeModelById(1)).thenReturn(employee);
        AddressDto result = addressService.getAddressByEmployeeId(1);
        assertNotNull(result);
        assertEquals(addressDto.getArea(), result.getArea());
    }

    @Test
    void testGetAddressByEmployeeIdThrowsException() {
        when(employeeService.getEmployeeModelById(1)).thenThrow(RuntimeException.class);
        assertThrows(CustomException.class, () -> addressService.getAddressByEmployeeId(1));
    }

    @Test
    void testGetAddressByEmployeeIdThrowsNoSuchElementException() {
        when(employeeService.getEmployeeModelById(1)).thenReturn(null);
        assertThrows(NoSuchElementException.class, () -> addressService.getAddressByEmployeeId(1));
    }

    @Test
    void testGetAddressById() throws CustomException {
        when(addressRepository.findByAddressIdAndIsDeletedFalse(1)).thenReturn(address);
        AddressDto result = addressService.getAddressById(1);
        assertNotNull(result);
        assertEquals(addressDto.getArea(), result.getArea());
    }

    @Test
    void testGetAddressByIdThrowsException() {
        when(addressRepository.findByAddressIdAndIsDeletedFalse(1)).thenThrow(RuntimeException.class);
        assertThrows(CustomException.class, () -> addressService.getAddressById(1));
    }

    @Test
    void testGetAddressByIdThrowsNoSuchElementException() {
        when(addressRepository.findByAddressIdAndIsDeletedFalse(1)).thenReturn(null);
        assertThrows(NoSuchElementException.class, () -> addressService.getAddressById(1));
    }

    @Test
    void testGetAddressByIdThrowsCustomException() {
        when(addressRepository.findByAddressIdAndIsDeletedFalse(1)).thenThrow(RuntimeException.class);
        assertThrows(CustomException.class, () -> addressService.getAddressById(1));
    }

    @Test
    void testDeleteAddress() {
        when(addressRepository.findByAddressIdAndIsDeletedFalse(1)).thenReturn(address);
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        addressService.deleteAddress(1);
    }

    @Test
    void testDeleteAddressThrowsCustomException() {
        when(addressRepository.findByAddressIdAndIsDeletedFalse(1)).thenThrow(RuntimeException.class);
        assertThrows(CustomException.class, () -> addressService.deleteAddress(1));
    }

}

