package com.i2i.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.i2i.ems.dto.ProjectDto;
import com.i2i.ems.helper.CustomException;
import com.i2i.ems.model.Project;
import com.i2i.ems.service.ProjectService;

/**
 * Controller to handle Project related requests.
 */
@RestController
@RequestMapping("v1/employees/{employeeId}/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * <p>
     * Adds a new project by collecting details from user input.
     * </p>
     *
     * @param projectDto to add new project.
     * @return the created projectDto object.
     * @throws CustomException when exception is thrown.
     */
    @PostMapping
    public ResponseEntity<ProjectDto> addProject(@Validated @RequestBody ProjectDto projectDto,
                                                 @PathVariable String employeeId)
            throws CustomException {
        return new ResponseEntity<>(projectService.addProject(projectDto), HttpStatus.CREATED);
    }

    /**
     * <p>
     * Prompts the user to enter new values for the project to update the Project.
     * </p>
     *
     * @param projectDto to update the project the details.
     * @return the updated projectDto.
     * @throws CustomException when exception is thrown.
     */
    @PutMapping
    public ResponseEntity<ProjectDto> updateProject(@Validated @RequestBody ProjectDto projectDto,
                                                    @PathVariable String employeeId)
            throws CustomException {
        return new ResponseEntity<>(projectService.updateProject(projectDto), HttpStatus.OK);
    }

    /**
     * <p>
     * Retrieves and displays the details of the project.
     * </p>
     *
     * @param projectId to retrieve the project
     * @return the retrieved project and the HttpStatus code.
     * @throws CustomException if exception occurred.
     */
    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable int projectId,
                                                  @PathVariable String employeeId)
            throws CustomException {
        return new ResponseEntity<>(projectService.getProjectById(projectId), HttpStatus.OK);
    }

    /**
     * <p>
     *  Deletes an Project based on the provided employee ID.
     * </p>
     *
     * @param id to delete the employee.
     * @return the HttpStatus code.
     * @throws CustomException if exception occurred.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> removeProject(@PathVariable int id,
                                                    @PathVariable String employeeId)
            throws CustomException {
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


