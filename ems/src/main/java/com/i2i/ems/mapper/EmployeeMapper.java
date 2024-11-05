package com.i2i.ems.mapper;

import java.util.stream.Collectors;

import com.i2i.ems.dto.AddressDto;
import com.i2i.ems.dto.EmployeeDto;
import com.i2i.ems.dto.ProjectDto;
import com.i2i.ems.model.Address;
import com.i2i.ems.model.Employee;
import com.i2i.ems.model.Project;

/**
 * EmployeeMapper is a class that provides methods to convert
 * between Employee model objects and EmployeeDto data transfer objects.
 */
public class EmployeeMapper {

    /**
     * <p>
     * This method converts the Employee model to EmployeeDto.
     * </p>
     *
     * @param employee model object to convert as EmployeeDto.
     * @return the converted EmployeeDto.
     */
    public static EmployeeDto modelToDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .role(employee.getRole())
                .dept(employee.getDept())
                .email(employee.getEmail())
                .mobileNumber(employee.getMobileNumber())

                .address((employee.getAddress() == null) ? null : AddressDto.builder()
                        .addressId(employee.getAddress().getAddressId())
                        .area(employee.getAddress().getArea())
                        .city(employee.getAddress().getCity())
                        .country(employee.getAddress().getCountry())
                        .build())

                .project((employee.getProject() == null) ? null : ProjectDto.builder()
                        .projectId(employee.getProject().getProjectId())
                        .name(employee.getProject().getName())
                        .head(employee.getProject().getDomain())
                        .domain(employee.getProject().getDomain())
                        .build())

                .qualifications((employee.getQualifications() == null) ? null : employee.getQualifications().stream()
                        .map(QualificationMapper::modelToDto)
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * <p>
     * This method converts the EmployeeDto to Employee model.
     * </p>
     *
     * @param employeeDto model object to convert as Employee model.
     * @return the converted Employee model.
     */
    public static Employee dtoToModel(EmployeeDto employeeDto) {
        return Employee.builder()
                .id(employeeDto.getId())
                .name(employeeDto.getName())
                .role(employeeDto.getRole())
                .dept(employeeDto.getDept())
                .email(employeeDto.getEmail())
                .mobileNumber(employeeDto.getMobileNumber())

                .address((employeeDto.getAddress() != null) ? AddressMapper.dtoToModel(employeeDto.getAddress()) : null)

                .project((employeeDto.getProject() == null) ? null : Project.builder()
                        .projectId(employeeDto.getProject().getProjectId())
                        .name(employeeDto.getProject().getName())
                        .domain(employeeDto.getProject().getDomain())
                        .head(employeeDto.getProject().getHead())
                        .build())

                .qualifications((employeeDto.getQualifications() == null) ? null : employeeDto.getQualifications().stream()
                        .map(QualificationMapper::dtoToModel)
                        .collect(Collectors.toList()))
                .build();
    }
}
