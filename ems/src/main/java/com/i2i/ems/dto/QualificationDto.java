package com.i2i.ems.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for Qualification information.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QualificationDto {

    private int qualificationId;

    @NotBlank(message = "CourseName is mandatory")
    @Pattern(regexp="^[a-zA-Z]+")
    private String courseName;

    @NotBlank(message = "Type is mandatory")
    @Pattern(regexp="^[a-zA-Z]+")
    private String courseType;

    @NotBlank(message = "Duration is mandatory")
    private String courseDuration;

}
