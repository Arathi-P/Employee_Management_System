package com.i2i.ems.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for Employee information.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDto {

    private int id;

    @NotBlank(message = "Name is mandatory")
    @Pattern(regexp = "^[a-zA-Z]+")
    private String name;

    @NotBlank(message = "Role is mandatory")
    @Pattern(regexp = "^[a-zA-Z]+", message = "Name should not have any Numbers")
    private String role;

    @NotBlank(message = "Department is mandatory")
    @Pattern(regexp = "^[a-zA-Z]+", message = "Email id is not valid")
    private String dept;

    @Email
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "mobileNumber is mandatory")
    @Pattern(regexp = "^[1-9][0-9]{9}$", message = " Mobile number should contain only numbers")
    private String mobileNumber;

    private AddressDto address;
    private List<QualificationDto> qualifications;
    private ProjectDto project;
}
