package com.i2i.ems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.i2i.ems.dto.EmployeeDto;
import com.i2i.ems.helper.CustomException;
import com.i2i.ems.service.EmployeeService;

/**
 * Controller to handle Employee related requests.
 */
@RestController
@RequestMapping("v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * <p>
     * Adds a new employee by collecting details from user input.
     * </p>
     *
     * @param employeeDto to add the employee.
     * @return the newly created Employee object and the HttpStatus.
     * @throws CustomException when exception is thrown.
     */
    @PostMapping("/register")
    public ResponseEntity<EmployeeDto> addEmployee(@Validated @RequestBody EmployeeDto employeeDto) throws CustomException {
        return new ResponseEntity<>(employeeService.addEmployee(employeeDto), HttpStatus.CREATED);
    }

    /**
     * <p>
     * Prompts the user to enter new values for the employee.
     * </p>
     *
     * @param employeeDto to update the all the details of the employee.
     * @return the updated Employee object and the HttpStatus.
     */
    @PutMapping
    public ResponseEntity<EmployeeDto> updateEmployee(@Validated @RequestBody EmployeeDto employeeDto) throws CustomException {
        return new ResponseEntity<>(employeeService.updateEmployee(employeeDto), HttpStatus.OK);
    }

    /**
     * <p>
     * Retrieves and displays the details of an employee.
     * </p>
     *
     * @param id the ID of the employee whose details are to be viewed
     * @return the Employee object and the HttpStatus code.
     * @throws CustomException if any exception occurs.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable int id) throws CustomException {
        EmployeeDto employeeDto = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    /**
     * <p>
     * Retrieves and displays all employees.
     * </p>
     *
     * @return {@link List<EmployeeDto>} all the Employees.
     * @throws CustomException, when any custom Exception is thrown.
     */

    @GetMapping //employees?page=0&size=10
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws CustomException {
        return new ResponseEntity<>(employeeService.getAllEmployees(page, size), HttpStatus.OK);
    }

    /**
     * <p>
     * Deletes an employee based on the provided ID.
     * </p>
     *
     * @param id of the employee for deleting
     * @return the HttpStatus code when deleted
     * @throws CustomException, if any exception occurs.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> removeEmployee(@PathVariable int id) throws CustomException {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * <p>
     * Login an employee based on the provided Username and password. Generates new token for an employee.
     * </p>
     *
     * @param employeeDto object
     * @return the created token as string
     * @throws CustomException .
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody EmployeeDto employeeDto) throws CustomException {
        return new ResponseEntity<>(employeeService.authenticateEmployee(employeeDto), HttpStatus.OK);
    }
}
