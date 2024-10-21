package com.i2i.ems.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.i2i.ems.dto.AddressDto;
import com.i2i.ems.helper.CustomException;
import com.i2i.ems.service.AddressService;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @Mock
    private AddressService addressService;
    private AddressDto addressDto;

    @InjectMocks
    private AddressController addressController;

    @BeforeEach
    void setUp() {
        addressDto = AddressDto.builder()
                .addressId(1)
                .area("Test area")
                .city("Test city")
                .country("test country")
                .build();
    }

    @Test
    void testAddAddress() throws CustomException {
        when(addressService.addAddress(any(AddressDto.class))).thenReturn(addressDto);
        ResponseEntity<AddressDto> response = addressController.addAddress(1, addressDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(addressDto, response.getBody());
        verify(addressService, times(1)).addAddress(any(AddressDto.class));
    }

    @Test
    void testUpdateAddress() throws CustomException {
        when(addressService.updateAddress(anyInt(), any(AddressDto.class))).thenReturn(addressDto);
        ResponseEntity<AddressDto> response = addressController.updateAddress(1, addressDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(addressDto, response.getBody());
        verify(addressService, times(1)).updateAddress(anyInt(), any(AddressDto.class));
    }

    @Test
    void testGetAddressById() throws CustomException {
        when(addressService.getAddressById(anyInt())).thenReturn(addressDto);
        ResponseEntity<AddressDto> response = addressController.getAddressById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(addressDto, response.getBody());
        verify(addressService, times(1)).getAddressById(1);
    }

    @Test
    void testRemoveAddress() throws CustomException {
        doNothing().when(addressService).deleteAddress(anyInt());
        ResponseEntity<HttpStatus> response = addressController.removeAddress(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(addressService, times(1)).deleteAddress(1);
    }
}