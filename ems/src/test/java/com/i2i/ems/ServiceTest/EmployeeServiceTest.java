package com.i2i.ems.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.i2i.ems.dto.EmployeeDto;
import com.i2i.ems.helper.CustomException;
import com.i2i.ems.model.Employee;
import com.i2i.ems.repository.EmployeeRepository;

class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    private Employee employee;
    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee();
        employee.setId(1);
        employee.setEmail("test@example.com");
        employee.setPassword(encoder.encode("password"));

        employeeDto = new EmployeeDto();
        employeeDto.setId(1);
        employeeDto.setEmail("test@example.com");
        employeeDto.setPassword("password");
    }

    @Test
    void testAddEmployee() throws CustomException {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        EmployeeDto result = employeeService.addEmployee(employeeDto);
        assertNotNull(result);
        assertEquals(employeeDto.getEmail(), result.getEmail());
    }

    @Test
    void testAddEmployeeThrowsException() {
        when(employeeRepository.save(any(Employee.class))).thenThrow(RuntimeException.class);
        assertThrows(CustomException.class, () -> employeeService.addEmployee(employeeDto));
    }

    @Test
    void testUpdateEmployee() throws CustomException {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        EmployeeDto result = employeeService.updateEmployee(employeeDto);
        assertNotNull(result);
        assertEquals(employeeDto.getEmail(), result.getEmail());
    }

    @Test
    void testUpdateEmployeeThrowsException() {
        when(employeeRepository.save(any(Employee.class))).thenThrow(RuntimeException.class);
        assertThrows(CustomException.class, () -> employeeService.updateEmployee(employeeDto));
    }

    @Test
    void testGetAllEmployees() throws CustomException {
        Pageable pageable = PageRequest.of(0, 10);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        Page<Employee> employeePage = new PageImpl<>(employeeList);

        when(employeeRepository.findAllByIsDeletedFalse(pageable)).thenReturn(employeePage);

        List<EmployeeDto> result = employeeService.getAllEmployees(0, 10);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(employeeDto.getEmail(), result.get(0).getEmail());
    }

    @Test
    void testGetAllEmployeesThrowsException() {
        Pageable pageable = PageRequest.of(0, 10);
        when(employeeRepository.findAllByIsDeletedFalse(pageable)).thenThrow(RuntimeException.class);
        assertThrows(CustomException.class, () -> employeeService.getAllEmployees(0, 10));
    }

    @Test
    void testGetEmployeeById() {
        when(employeeRepository.findByIdAndIsDeletedFalse(1)).thenReturn(employee);
        EmployeeDto result = employeeService.getEmployeeById(1);
        assertNotNull(result);
        assertEquals(employeeDto.getEmail(), result.getEmail());
    }

    @Test
    void testGetEmployeeByIdThrowsException() {
        when(employeeRepository.findByIdAndIsDeletedFalse(1)).thenThrow(RuntimeException.class);
        assertThrows(NoSuchElementException.class, () -> employeeService.getEmployeeById(1));
    }

    @Test
    void testDeleteEmployee() throws CustomException {
        when(employeeRepository.findByIdAndIsDeletedFalse(1)).thenReturn(employee);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);  // Correctly mock save method

        employeeService.deleteEmployee(1);

        verify(employeeRepository, times(1)).save(any(Employee.class));
    }


    @Test
    void testDeleteEmployeeThrowsNoSuchElementException() {
        // Mocking the repository to return null to simulate employee not found
        when(employeeRepository.findByIdAndIsDeletedFalse(1)).thenReturn(null);

        // Expect NoSuchElementException
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> employeeService.deleteEmployee(1));

        assertEquals("No Employee found with id: 1", exception.getMessage());
    }


    @Test
    void testAuthenticateEmployee() throws CustomException {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(employeeDto.getEmail(), employeeDto.getPassword()));
        String token = employeeService.authenticateEmployee(employeeDto);
        assertNotNull(token);
    }

    @Test
    void testAuthenticateEmployeeThrowsException() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(BadCredentialsException.class);
        assertThrows(CustomException.class, () -> employeeService.authenticateEmployee(employeeDto));
    }
}