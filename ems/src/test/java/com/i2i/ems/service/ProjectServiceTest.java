package com.i2i.ems.service;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.i2i.ems.dto.ProjectDto;
import com.i2i.ems.helper.CustomException;
import com.i2i.ems.model.Employee;
import com.i2i.ems.model.Project;
import com.i2i.ems.repository.ProjectRepository;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    private Employee employee;
    private Project project;
    private ProjectDto projectDto;

    @BeforeEach
    void setUp() {
        project = Project.builder()
                .projectId(1)
                .name("testName")
                .domain("testDomain")
                .head("testHead")
                .build();

        projectDto = ProjectDto.builder()
                .projectId(1)
                .name("testName")
                .domain("testDomain")
                .head("testHead")
                .build();

        employee = Employee.builder()
                .id(1)
                .project(project)
                .build();
    }

    @Test
    void testAddProject() throws CustomException {
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        ProjectDto result = projectService.addProject(projectDto);
        assertNotNull(result);
        assertEquals(projectDto.getName(), result.getName());
    }

    @Test
    void testAddProjectThrowsException() {
        when(projectRepository.save(any(Project.class))).thenThrow(RuntimeException.class);
        assertThrows(CustomException.class, () -> projectService.addProject(projectDto));
    }

    @Test
    void testGetProjectById() throws CustomException {
        when(projectRepository.findByProjectIdAndIsDeletedFalse(1)).thenReturn(project);
        Project result = projectService.getProjectById(1);
        assertNotNull(result);
        assertEquals(projectDto.getName(), result.getName());
    }

    @Test
    void testGetProjectByIdThrowsException() {
        when(projectRepository.findByProjectIdAndIsDeletedFalse(1)).thenThrow(RuntimeException.class);
        assertThrows(CustomException.class, () -> projectService.getProjectById(1));
    }

    @Test
    void testGetProjectByIdThrowsNoSuchElementException() {
        when(projectRepository.findByProjectIdAndIsDeletedFalse(1)).thenReturn(null);
        assertThrows(NoSuchElementException.class, () -> projectService.getProjectById(1));
    }

    @Test
    void testUpdateProject() throws CustomException {
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        ProjectDto result = projectService.updateProject(projectDto);
        assertNotNull(result);
        assertEquals(projectDto.getName(), result.getName());
    }

    @Test
    void testUpdateProjectThrowsException() {
        when(projectRepository.save(any(Project.class))).thenThrow(RuntimeException.class);
        assertThrows(CustomException.class, () -> projectService.updateProject(projectDto));
    }

    @Test
    void testDeleteProject() throws CustomException {
        when(projectRepository.findByProjectIdAndIsDeletedFalse(1)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(project);
        projectService.deleteProject(1);
    }

    @Test
    void testDeleteProjectThrowsException() {
        when(projectRepository.findByProjectIdAndIsDeletedFalse(1)).thenThrow(RuntimeException.class);
        assertThrows(CustomException.class, () -> projectService.deleteProject(1));
    }

}
