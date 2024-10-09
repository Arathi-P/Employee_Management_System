package com.i2i.ems.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for Task information.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TaskDto {
    private int taskId;

    @NotBlank(message = "TaskName is mandatory")
    @Pattern(regexp="^[a-zA-Z]")
    private String taskName;

    @NotBlank(message = "Type is mandatory")
    @Pattern(regexp="^[a-zA-Z]")
    private String type;

    @NotBlank(message = "Task status is mandatory")
    @Pattern(regexp="^[a-zA-Z]")
    private String status;
}
