package com.i2i.ems.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.i2i.ems.dto.ProjectDto;
import com.i2i.ems.helper.CustomException;
import com.i2i.ems.model.Project;
import com.i2i.ems.service.ProjectService;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    private ProjectDto projectDto;
    private Project project;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    void setUp() {
        projectDto = ProjectDto.builder()
                .projectId(1)
                .name("Name")
                .domain("Domain")
                .head("Head")
                .build();
    }

    @Test
    void testAddProject() throws CustomException {
        when(projectService.addProject(any(ProjectDto.class))).thenReturn(projectDto);
        ResponseEntity<ProjectDto> response = projectController.addProject(projectDto, "1");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(projectDto, response.getBody());
        verify(projectService, times(1)).addProject(any(ProjectDto.class));
    }

    @Test
    void testUpdateProject() throws CustomException {
        when(projectService.updateProject(any(ProjectDto.class))).thenReturn(projectDto);
        ResponseEntity<ProjectDto> response = projectController.updateProject(projectDto, "1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projectDto, response.getBody());
        verify(projectService, times(1)).updateProject(any(ProjectDto.class));
    }

    @Test
    void testGetProjectById() throws CustomException {
        when(projectService.getProjectById(anyInt())).thenReturn(project);
        ResponseEntity<Project> response = projectController.getProjectById(1, "1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(project, response.getBody());
        verify(projectService, times(1)).getProjectById(1);
    }

    @Test
    void testRemoveProject() throws CustomException {
        doNothing().when(projectService).deleteProject(anyInt());
        ResponseEntity<HttpStatus> response = projectController.removeProject(1, "1");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(projectService, times(1)).deleteProject(1);
    }
}