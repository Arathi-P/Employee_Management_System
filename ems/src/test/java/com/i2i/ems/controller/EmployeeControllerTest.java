package com.i2i.ems.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import com.i2i.ems.dto.EmployeeDto;
import com.i2i.ems.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {


    @Mock
    private EmployeeService employeeService;

    private EmployeeDto employeeDto;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        employeeDto = EmployeeDto.builder()
                .id(1)
                .name("test")
                .build();
    }

    @Test
    void testAddEmployee() {
        when(employeeService.addEmployee(any(EmployeeDto.class))).thenReturn(employeeDto);
        ResponseEntity<EmployeeDto> response = employeeController.addEmployee(employeeDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employeeDto, response.getBody());
        verify(employeeService, times(1)).addEmployee(any(EmployeeDto.class));
    }

    @Test
    void testUpdateEmployee() {
        when(employeeService.updateEmployee(any(EmployeeDto.class))).thenReturn(employeeDto);
        ResponseEntity<EmployeeDto> response = employeeController.updateEmployee(employeeDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeDto, response.getBody());
        verify(employeeService, times(1)).updateEmployee(employeeDto);
    }

    @Test
    void testGetEmployeeById() {
        when(employeeService.getEmployeeById(anyInt())).thenReturn(employeeDto);
        ResponseEntity<EmployeeDto> response = employeeController.getEmployeeById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("test", Objects.requireNonNull(response.getBody()).getName());
        verify(employeeService, times(1)).getEmployeeById(anyInt());
    }

    @Test
    void testGetAllEmployees() {
        List<EmployeeDto> employees = Arrays.asList(employeeDto);
        when(employeeService.getAllEmployees(0, 1)).thenReturn(employees);
        ResponseEntity<List<EmployeeDto>> response = employeeController.getAllEmployees(0, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employees, response.getBody());
        verify(employeeService, times(1)).getAllEmployees(0, 1);
    }

    @Test
    void testRemoveEmployee() {
        doNothing().when(employeeService).deleteEmployee(anyInt());
        ResponseEntity<HttpStatus> response = employeeController.removeEmployee(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(employeeService, times(1)).deleteEmployee(1);
    }

    @Test
    void testLogin() {
        when(employeeService.authenticateEmployee(any(EmployeeDto.class))).thenReturn("token123");
        ResponseEntity<String> response = employeeController.login(employeeDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(employeeService, times(1)).authenticateEmployee(any(EmployeeDto.class));
    }
}