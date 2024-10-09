package com.i2i.ems.service;

import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.i2i.ems.dto.QualificationDto;
import com.i2i.ems.helper.CustomException;
import com.i2i.ems.mapper.QualificationMapper;
import com.i2i.ems.model.Qualification;
import com.i2i.ems.repository.QualificationRepository;

@Service
public class QualificationService {

    private static final Logger logger = LogManager.getLogger(QualificationService.class);

    @Autowired
    private QualificationRepository qualificationRepository;

    /**
     * <p>
     * Adds a new qualification by collecting details from user input.
     * </p>
     *
     * @param qualificationDto to add new qualification.
     * @return the created Qualification.
     * @throws CustomException when exception is thrown.
     */
    public QualificationDto addQualification(QualificationDto qualificationDto, int employeeId) throws CustomException {
        try {
            Qualification qualification = QualificationMapper.dtoToModel((qualificationDto));
            qualificationRepository.save(qualification);
            QualificationDto qualificationDto1 = QualificationMapper.modelToDto(qualification);
            logger.info("Qualification added with the ID : {} ", qualificationDto1.getQualificationId());
            return qualificationDto1;
        } catch (Exception e) {
            logger.error("Error adding employee",e);
            throw new CustomException("Server Error!!!!", e);
        }
    }

    /**
     * <p>
     * Retrieves and displays the details of the qualification.
     * </p>
     *
     * @param id to retrieve the Qualification.
     * @return the retrieved the qualification
     * @throws NoSuchElementException, CustomException if occurred.
     */
    public Qualification getQualificationById(int id) throws NoSuchElementException, CustomException {
        try {
            logger.info("Retrieved qualification details for ID: {}", id);
            return qualificationRepository.findByQualificationIdAndIsDeletedFalse(id);
        } catch (NoSuchElementException e) {
            logger.error("Error in retrieving the employee with the given id : {}", id, e);
            throw new NoSuchElementException("No Qualification found with id: " + id, e);
        } catch (Exception e) {
            logger.error("Error in retrieving the employee with the given id : {}", id, e);
            throw new CustomException("Server Error!!!!", e);
        }
    }

    public QualificationDto updateQualification(QualificationDto qualificationDto) throws CustomException {
        try {
            Qualification qualification = QualificationMapper.dtoToModel((qualificationDto));
            qualificationRepository.save(qualification);
            logger.info("Qualification updated successfully for ID: {}", qualificationDto.getQualificationId());
            return QualificationMapper.modelToDto((qualification));
        } catch (Exception e) {
            logger.error("Error in updating the qualification with the given id : {}", qualificationDto.getQualificationId(), e);
            throw new CustomException("Server Error!!!!", e);
        }
    }

    public void deleteQualification(int id) throws CustomException, NoSuchElementException {
        try {
            Qualification qualification = qualificationRepository.findByQualificationIdAndIsDeletedFalse(id);
            qualification.setDeleted(true);
            qualificationRepository.save(qualification);
            logger.info("Qualification removed successfully with ID: {}", id);
        } catch (NoSuchElementException e) {
            logger.error("Error in removing the employee : {} ", id, e);
            throw new NoSuchElementException("No Qualification found with id: " + id, e);
        } catch (Exception e) {
            logger.error("Error in removing the employee : {} ", id, e);
            throw new CustomException("Server Error!!!!", e);
        }
    }
}
