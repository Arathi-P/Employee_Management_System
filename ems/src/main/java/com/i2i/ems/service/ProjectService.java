package com.i2i.ems.service;

import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.i2i.ems.dto.ProjectDto;
import com.i2i.ems.helper.CustomException;
import com.i2i.ems.mapper.ProjectMapper;
import com.i2i.ems.model.Project;
import com.i2i.ems.repository.ProjectRepository;


@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    private static final Logger logger = LogManager.getLogger(ProjectService.class);

    /**
     * <p>
     * Adds a new project by collecting details from user input.
     * </p>
     *
     * @param projectDto to add new project.
     * @return the created projectDto object.
     * @throws CustomException when exception is thrown.
     */
    public ProjectDto addProject(ProjectDto projectDto) throws CustomException {
        try {
            Project project = ProjectMapper.dtoToModel((projectDto));
            projectRepository.save(project);
            ProjectDto projectDto1 = ProjectMapper.modelToDto(project);
            logger.info("Project added successfully with ID: {}", projectDto1.getProjectId());
            return projectDto1;
        } catch (Exception e) {
            logger.error("Error adding employee", e);
            throw new CustomException("Server error!!!",e);
        }
    }

    /**
     * <p>
     * Retrieves and displays the details of the project.
     * </p>
     *
     * @param id to retrieve the project
     * @return the retrieved project.
     * @throws CustomException if exception occurred.
     */
    public Project getProjectById(int id) throws NoSuchElementException, CustomException {
        try {
            logger.info("Retrieved project details for ID: {}", id);
            Project project = projectRepository.findByProjectIdAndIsDeletedFalse(id);
            if (project == null) {
                throw new NoSuchElementException("No project found with ID: " + id);
            }
            return project;
        } catch (NoSuchElementException e) {
            logger.error("No project found with ID : {}", id, e);
            throw e;
        } catch (Exception e) {
            logger.error("Error in retrieving employee with given id : {}", id, e);
            throw new CustomException("Server Error!!!!", e);
        }
    }

    /**
     * <p>
     * updates the project.
     * </p>
     *
     * @param projectDto to update the project the details.
     * @return the updated projectDto.
     * @throws CustomException when exception is thrown.
     */
    public ProjectDto updateProject(ProjectDto projectDto) throws CustomException {
        try {
            Project project = ProjectMapper.dtoToModel((projectDto));
            projectRepository.save(project);
            logger.info("Project updated successfully for ID: {}", projectDto.getProjectId());
            return ProjectMapper.modelToDto((project));
        } catch (Exception e) {
            logger.error("Error in updating employee with the id : {}", projectDto.getProjectId(), e);
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * <p>
     * Deletes an Project based on the provided employee ID.
     * </p>
     *
     * @param id to delete the employee.
     * @throws NoSuchElementException, CustomException if exception occurred.
     */
    public void deleteProject(int id) throws CustomException {
        try {
            Project project = getProjectById(id);
            project.setDeleted(true);
            projectRepository.save(project);
            logger.info("Project removed successfully with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error in removing the project with the given id : {}", id, e);
            throw new CustomException("Server Error!!!!", e);
        }
    }
}
