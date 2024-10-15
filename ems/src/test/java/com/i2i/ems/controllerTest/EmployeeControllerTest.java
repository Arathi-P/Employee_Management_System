package com.i2i.ems.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.i2i.ems.controller.EmployeeController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.i2i.ems.dto.EmployeeDto;
import com.i2i.ems.helper.CustomException;
import com.i2i.ems.service.EmployeeService;

class EmployeeControllerTest {


    @Mock
    private EmployeeService employeeService;

    private EmployeeDto employeeDto;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        employeeDto = new EmployeeDto();
        employeeDto.setId(1);
        employeeDto.setName("Arthi");
    }

    @Test
    void testAddEmployee() throws CustomException {
        when(employeeService.addEmployee(any(EmployeeDto.class))).thenReturn(employeeDto);

        ResponseEntity<EmployeeDto> response = employeeController.addEmployee(employeeDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employeeDto, response.getBody());
        verify(employeeService, times(1)).addEmployee(any(EmployeeDto.class));
    }

    @Test
    void testUpdateEmployee() throws CustomException {
        when(employeeService.updateEmployee(any(EmployeeDto.class))).thenReturn(employeeDto);

        ResponseEntity<EmployeeDto> response = employeeController.updateEmployee(employeeDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Arthi", response.getBody().getName());
        verify(employeeService, times(1)).updateEmployee(any(EmployeeDto.class));
    }

    @Test
    void testGetEmployeeById() throws CustomException {
        when(employeeService.getEmployeeById(anyInt())).thenReturn(employeeDto);

        ResponseEntity<EmployeeDto> response = employeeController.getEmployeeById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Arthi", response.getBody().getName());
        verify(employeeService, times(1)).getEmployeeById(anyInt());
    }

    @Test
    void testGetAllEmployees() throws CustomException {
        List<EmployeeDto> employees = new ArrayList<>();
        employees.add(employeeDto);
        EmployeeDto employeeDto2 = new EmployeeDto();
        employeeDto2.setId(2);
        employeeDto2.setName("Jane");
        employees.add(employeeDto2);

        when(employeeService.getAllEmployees(0, 10)).thenReturn(employees);

        ResponseEntity<List<EmployeeDto>> response = employeeController.getAllEmployees(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Arthi", response.getBody().get(0).getName());
        assertEquals("Jane", response.getBody().get(1).getName());
        verify(employeeService, times(1)).getAllEmployees(0, 10);
    }

    @Test
    void testRemoveEmployee() throws CustomException {
        ResponseEntity<HttpStatus> response = employeeController.removeEmployee(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(employeeService, times(1)).deleteEmployee(1);
    }

    @Test
    void testLogin() throws CustomException {
        employeeDto.setEmail("test@gmail.com");
        employeeDto.setPassword("password");

        when(employeeService.authenticateEmployee(any(EmployeeDto.class))).thenReturn("token123");

        String token = employeeController.login(employeeDto);

        assertEquals("token123", token);
        verify(employeeService, times(1)).authenticateEmployee(any(EmployeeDto.class));
    }
}