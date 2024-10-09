package com.i2i.ems.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for Address information.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressDto {
    private int addressId;

    @NotBlank(message = "Area is mandatory")
    @Pattern(regexp="^[a-zA-Z]+")
    private String area;

    @NotBlank(message = "City is mandatory")
    @Pattern(regexp="^[a-zA-Z]+")
    private String city;

    @NotBlank(message = "Country is mandatory")
    @Pattern(regexp="^[a-zA-Z]+")
    private String country;
}

