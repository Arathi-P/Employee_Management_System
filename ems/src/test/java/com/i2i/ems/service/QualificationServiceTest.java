package com.i2i.ems.service;

import com.i2i.ems.dto.QualificationDto;
import com.i2i.ems.helper.CustomException;
import com.i2i.ems.model.Employee;
import com.i2i.ems.model.Qualification;
import com.i2i.ems.repository.QualificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class QualificationServiceTest {

    @Mock
    private QualificationRepository qualificationRepository;

    @InjectMocks
    private QualificationService qualificationService;

    private Qualification qualification;
    private QualificationDto qualificationDto;
    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        qualification = Qualification.builder()
                .qualificationId(1)
                .courseName("testName")
                .courseType("testType")
                .courseDuration("testDuration")
                .build();

        qualificationDto = QualificationDto.builder()
                .qualificationId(1)
                .courseName("testName")
                .courseType("testType")
                .courseDuration("testDuration")
                .build();

        employee = Employee.builder()
                .id(1)
                .qualifications(List.of(qualification))
                .build();
    }

    @Test
    void testAddQualification() throws CustomException {
        when(qualificationRepository.save(any(Qualification.class))).thenReturn(qualification);
        QualificationDto result = qualificationService.addQualification(qualificationDto, anyInt());
        assertNotNull(result);
        assertEquals(qualificationDto.getCourseName(), result.getCourseName());
    }

    @Test
    void testAddQualificationThrowsException() {
        when(qualificationRepository.save(any(Qualification.class))).thenThrow(RuntimeException.class);
        assertThrows(CustomException.class, () -> qualificationService.updateQualification(qualificationDto));
    }

    @Test
    void testGetQualificationById() throws CustomException {
        when(qualificationRepository.findByQualificationIdAndIsDeletedFalse(1)).thenReturn(qualification);
        Qualification result = qualificationService.getQualificationById(1);
        assertNotNull(result);
        assertEquals(qualificationDto.getCourseName(), result.getCourseName());
    }

    @Test
    void testGetQualificationByIdThrowsException() {
        when(qualificationRepository.findByQualificationIdAndIsDeletedFalse(1)).thenThrow(RuntimeException.class);
        assertThrows(CustomException.class, () -> qualificationService.getQualificationById(1));
    }

    @Test
    void testGetQualificationByIdThrowsNoSuchElementException() {
        when(qualificationRepository.findByQualificationIdAndIsDeletedFalse(1)).thenReturn(null);
        assertThrows(NoSuchElementException.class, () -> qualificationService.getQualificationById(1));
    }

    @Test
    void testUpdateQualification() throws CustomException {
        when(qualificationRepository.save(any(Qualification.class))).thenReturn(qualification);
        QualificationDto result = qualificationService.updateQualification(qualificationDto);
        assertNotNull(result);
        assertEquals(qualificationDto.getCourseName(), result.getCourseName());
    }

    @Test
    void testUpdateQualificationThrowsException() {
        when(qualificationRepository.save(any(Qualification.class))).thenThrow(RuntimeException.class);
        assertThrows(CustomException.class, () -> qualificationService.updateQualification(qualificationDto));
    }

    @Test
    void testDeleteQualification() throws CustomException {
        when(qualificationRepository.findByQualificationIdAndIsDeletedFalse(1)).thenReturn(qualification);
        when(qualificationRepository.save(any(Qualification.class))).thenReturn(qualification);
        qualificationService.deleteQualification(1);
    }

    @Test
    void testDeleteQualificationThrowsException() {
        when(qualificationRepository.findByQualificationIdAndIsDeletedFalse(1)).thenThrow(RuntimeException.class);
        assertThrows(CustomException.class, () -> qualificationService.deleteQualification(1));
    }

    @Test
    void testDeleteQualificationThrowsNoSuchElementException() {
        when(qualificationRepository.findByQualificationIdAndIsDeletedFalse(1)).thenReturn(null);
        assertThrows(NoSuchElementException.class, () -> qualificationService.deleteQualification(1));
    }
}
