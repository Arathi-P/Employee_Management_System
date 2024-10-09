package com.i2i.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.i2i.ems.dto.QualificationDto;
import com.i2i.ems.helper.CustomException;
import com.i2i.ems.model.Qualification;
import com.i2i.ems.service.QualificationService;

/**
 * Controller to handle Qualification related requests.
 */
@RestController
@RequestMapping("v1/employees/{employeeId}/qualifications")
public class QualificationController {

    @Autowired
    private QualificationService qualificationService;

    /**
     * <p>
     * Adds a new qualification by collecting details from user input.
     * </p>
     *
     * @param qualificationDto to add new qualification.
     * @return the created Qualification and the HttpStatus code.
     * @throws CustomException when exception is thrown.
     */
    @PostMapping
    public ResponseEntity<QualificationDto> addQualification(@Validated @RequestBody QualificationDto qualificationDto, @PathVariable Integer employeeId) throws CustomException {
        return new ResponseEntity<>(qualificationService.addQualification(qualificationDto, employeeId), HttpStatus.CREATED);
    }

    /**
     * <p>
     * Prompts the user to enter new values for the qualification to update the qualification.
     * </p>
     *
     * @param qualificationDto to update the qualification details.
     * @return the updated qualificationDto object and the HttpStatus code.
     * @throws CustomException when exception is thrown.
     */
    @PutMapping
    public ResponseEntity<QualificationDto> updateQualification(@Validated @RequestBody
                                                                QualificationDto qualificationDto,
                                                                @PathVariable String employeeId)
            throws CustomException {
        return new ResponseEntity<>(qualificationService.updateQualification(qualificationDto), HttpStatus.OK);
    }

    /**
     * <p>
     * Retrieves and displays the details of the qualification.
     * </p>
     *
     * @param id to retrieve the Qualification.
     * @return the retrieved the qualification
     * @throws CustomException, if occurred.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Qualification> getQualificationById(@PathVariable int id,
                                                              @PathVariable String employeeId)
            throws CustomException {
        return new ResponseEntity<>(qualificationService.getQualificationById(id), HttpStatus.OK);
    }

    /**
     * <p>
     * Deletes qualification based on the provided qualificationId.
     * </p>
     *
     * @param id to remove the qualification.
     * @return the HttpStatus code.
     * @throws CustomException if occurred.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> removeQualification(@PathVariable int id,
                                                          @PathVariable String employeeId)
            throws CustomException {
        qualificationService.deleteQualification(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


