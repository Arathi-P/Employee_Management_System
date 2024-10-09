package com.i2i.ems.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for project information.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProjectDto {
    private int projectId;

    @NotBlank(message = "ProjectName is mandatory")
    @Pattern(regexp="^[a-zA-Z]+")
    private String name;

    @NotBlank(message = "Domain is mandatory")
    @Pattern(regexp="^[a-zA-Z]+")

    private String domain;

    @NotBlank(message = "ProjectHead is mandatory")
    @Pattern(regexp="^[a-zA-Z]+")
    private String head;
}
