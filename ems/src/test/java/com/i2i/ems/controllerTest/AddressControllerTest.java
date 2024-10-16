package com.i2i.ems.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.i2i.ems.controller.AddressController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.i2i.ems.dto.AddressDto;
import com.i2i.ems.helper.CustomException;
import com.i2i.ems.service.AddressService;

class AddressControllerTest {

    @Mock
    private AddressService addressService;
    private AddressDto addressDto;

    @InjectMocks
    private AddressController addressController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        addressDto = new AddressDto();
        addressDto.setAddressId(1);
        addressDto.setArea("Test area");
        addressDto.setCity("Test city");
        addressDto.setCountry("test country");
    }

    @Test
    void testAddAddress() throws CustomException {
        when(addressService.addAddress(anyInt(), any(AddressDto.class))).thenReturn(addressDto);
        ResponseEntity<AddressDto> response = addressController.addAddress(1, addressDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(addressDto, response.getBody());
        verify(addressService, times(1)).addAddress(anyInt(), any(AddressDto.class));
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