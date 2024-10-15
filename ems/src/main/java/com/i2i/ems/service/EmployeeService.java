package com.i2i.ems.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.i2i.ems.dto.EmployeeDto;
import com.i2i.ems.helper.CustomException;
import com.i2i.ems.mapper.EmployeeMapper;
import com.i2i.ems.model.Address;
import com.i2i.ems.model.Employee;
import com.i2i.ems.repository.EmployeeRepository;
import com.i2i.ems.util.JwtUtil;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final Logger logger = LogManager.getLogger(EmployeeService.class);
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    /**
     * <p>
     * Creates a new Employee object and saves it in the repository.
     * </p>
     *
     * @param employeeDTO to create new employee.
     * @return the created employeeDto object.
     * @throws CustomException, DuplicateKeyException if exception is thrown.
     */
    public EmployeeDto addEmployee(EmployeeDto employeeDTO) throws CustomException {
        try {
            Employee employee = EmployeeMapper.dtoToModel((employeeDTO));
            if (employeeRepository.existsByEmailAndIsDeletedByFalse(employeeDTO.getEmail())) {
                throw new DuplicateKeyException("Employee exists with same Email Id");
            }
            employee.setPassword(encoder.encode(employeeDTO.getPassword()));
            employeeRepository.save(employee);
            EmployeeDto employeeDto = EmployeeMapper.modelToDto((employee));
            logger.info("Employee added successfully with ID: {}", employeeDto.getId());
            return employeeDto;
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                logger.error("Employee already exists with same Email Id");
                throw e;
            }
            logger.error("Error adding an employee", e);
            throw new CustomException("Server Error!!!!", e);
        }
    }

    /**
     * <p>
     * Updates the employee with new details.
     * </p>
     *
     * @param employeeDto to update the all the details of the employee.
     * @return the updated Employee object.
     * @throws CustomException when exception is thrown.
     */
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) throws CustomException {
        try {
            Employee employee = EmployeeMapper.dtoToModel((employeeDto));
            employeeRepository.save(employee);
            logger.info("Employee updated successfully for ID: {}", employeeDto.getId());
            return EmployeeMapper.modelToDto((employee));
        } catch (Exception e) {
            logger.error("Error updating an employee", e);
            throw new CustomException("Server Error!!!!", e);
        }
    }

    /**
     * <p>
     * Retrieves and displays all employees.
     * </p>
     *
     * @return {@link List<EmployeeDto>} all the Employees.
     * @throws CustomException, when any custom Exception is thrown.
     */
    public List<EmployeeDto> getAllEmployees(int page, int size) throws CustomException {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Employee> employeePage = employeeRepository.findAllByIsDeletedFalse(pageable);
            logger.info("Displayed employee details for page : {}", page);
            return employeePage.getContent().stream()
                    .map(EmployeeMapper::modelToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error in retrieving all employees", e);
            throw new CustomException("Server Error!!!!", e);
        }
    }

    /**
     * <p>
     * Retrieves and displays the details of an employee.
     * </p>
     *
     * @param id the ID of the employee whose details are to be viewed
     * @return the Employee object.
     * @throws NoSuchElementException when occurred.
     */
    public EmployeeDto getEmployeeById(int id) throws NoSuchElementException {
        try {
            Employee employee = employeeRepository.findByIdAndIsDeletedFalse(id);
            logger.info("Retrieved employee details for ID: {}", id);
            return EmployeeMapper.modelToDto(employee);
        } catch (Exception e) {
            logger.error("Error in retrieving an employee : {}", id, e);
            throw new NoSuchElementException("No Employee found with id: " + id, e);
        }
    }

    /**
     * <p>
     * Deletes an employee based on the provided ID.
     * </p>
     *
     * @param id of the employee for deleting.
     * @throws NoSuchElementException and CustomException.
     */
    public void deleteEmployee(int id) throws NoSuchElementException, CustomException {
        try {
            Employee employee = employeeRepository.findByIdAndIsDeletedFalse(id);
            Address address = employee.getAddress();
            if (null != address) {
                address.setDeleted(true);
            }
            employee.setDeleted(true);
            employeeRepository.save(employee);
            logger.info("Employee removed successfully with ID: {}", id);
        } catch (NoSuchElementException e) {
            logger.error("Error in removing an employee : {}", id, e);
            throw new NoSuchElementException("No Employee found with id: " + id, e);
        } catch (Exception e) {
            logger.error("Error in removing an employee : {}", id, e);
            throw new CustomException("Server Error!!!!", e);
        }
    }

    /**
     * <p>
     * Login an employee based on the provided Username and password. Generates new token for an employee.
     * </p>
     *
     * @param employeeDTO object.
     * @return the created token as string.
     * @throws CustomException .
     */
    public String authenticateEmployee(EmployeeDto employeeDTO) throws CustomException {
        try {
            authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    employeeDTO.getEmail(), employeeDTO.getPassword()
                            )
                    );
            return JwtUtil.generateToken(employeeDTO.getEmail());
        } catch (BadCredentialsException e) {
            logger.error("Error in employee login", e);
            throw new CustomException("Invalid Username or Password", e);
        }
    }
}
