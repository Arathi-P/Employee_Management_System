package com.i2i.ems.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.i2i.ems.controller.QualificationController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.i2i.ems.dto.QualificationDto;
import com.i2i.ems.helper.CustomException;
import com.i2i.ems.model.Qualification;
import com.i2i.ems.service.QualificationService;

class QualificationControllerTest {

    @Mock
    private QualificationService qualificationService;

    private QualificationDto qualificationDto;
    private Qualification qualification;

    @InjectMocks
    private QualificationController qualificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        qualificationDto = QualificationDto.builder()
                .qualificationId(1)
                .courseName("Test Qualification")
                .courseType("Test Institute")
                .courseDuration("2")
                .build();

        qualification = new Qualification();
        qualification.setQualificationId(1);
        qualification.setCourseName("Test Qualification");
        qualification.setCourseType("Test Institute");
        qualification.setCourseDuration("2");
    }

    @Test
    void testAddQualification() throws CustomException {
        when(qualificationService.addQualification(any(QualificationDto.class), anyInt()))
                .thenReturn(qualificationDto);

        ResponseEntity<QualificationDto> response = qualificationController.addQualification(qualificationDto, 1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(qualificationDto, response.getBody());
        verify(qualificationService, times(1)).addQualification(any(QualificationDto.class), anyInt());
    }

    @Test
    void testUpdateQualification() throws CustomException {
        when(qualificationService.updateQualification(any(QualificationDto.class)))
                .thenReturn(qualificationDto);

        ResponseEntity<QualificationDto> response = qualificationController.updateQualification(qualificationDto, "1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(qualificationDto, response.getBody());
        verify(qualificationService, times(1)).updateQualification(any(QualificationDto.class));
    }

    @Test
    void testGetQualificationById() throws CustomException {
        when(qualificationService.getQualificationById(anyInt())).thenReturn(qualification);

        ResponseEntity<Qualification> response = qualificationController.getQualificationById(1, "1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(qualification, response.getBody());
        verify(qualificationService, times(1)).getQualificationById(anyInt());
    }

    @Test
    void testRemoveQualification() throws CustomException {
        doNothing().when(qualificationService).deleteQualification(anyInt());

        ResponseEntity<HttpStatus> response = qualificationController.removeQualification(1, "1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(qualificationService, times(1)).deleteQualification(anyInt());
    }
}