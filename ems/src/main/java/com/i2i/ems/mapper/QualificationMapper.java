package com.i2i.ems.mapper;

import com.i2i.ems.dto.QualificationDto;
import com.i2i.ems.model.Qualification;

/**
 * QualificationMapper is a class that provides methods to convert
 * between Qualification model objects and QualificationDto data transfer objects.
 */
public class QualificationMapper {
    public static QualificationDto modelToDto(Qualification qualification) {
        return QualificationDto.builder()
                .qualificationId(qualification.getQualificationId())
                .courseName(qualification.getCourseName())
                .courseType(qualification.getCourseType())
                .courseDuration(qualification.getCourseDuration())
                .build();
    }

    public static Qualification dtoToModel(QualificationDto qualificationDto) {
        return Qualification.builder()
                .qualificationId(qualificationDto.getQualificationId())
                .courseName(qualificationDto.getCourseName())
                .courseType(qualificationDto.getCourseType())
                .courseDuration(qualificationDto.getCourseDuration())
                .build();
    }
}
