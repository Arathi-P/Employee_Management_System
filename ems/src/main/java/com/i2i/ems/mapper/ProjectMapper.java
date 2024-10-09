package com.i2i.ems.mapper;

import com.i2i.ems.dto.ProjectDto;
import com.i2i.ems.model.Project;

/**
 * ProjectMapper is a class that provides methods to convert
 * between Project model objects and ProjectDto data transfer objects.
 */
public class ProjectMapper {
    public static ProjectDto modelToDto(Project project) {
        return ProjectDto.builder()
                .projectId(project.getProjectId())
                .name(project.getHead())
                .domain(project.getDomain())
                .head(project.getHead())
                .build();
    }

    public static Project dtoToModel(ProjectDto projectDto) {
        return Project.builder()
                .projectId(projectDto.getProjectId())
                .name(projectDto.getHead())
                .domain(projectDto.getDomain())
                .head(projectDto.getHead())
                .build();
    }
}
