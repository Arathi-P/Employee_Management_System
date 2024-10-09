package com.i2i.ems.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a qualification entity in the database.
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "qualification")
@Getter
@Setter
@Builder
public class Qualification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qualification_id")
    private int qualificationId;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_type")
    private String courseType;

    @Column(name = "course_duration")
    private String courseDuration;

    @Column(name = "is_deleted")
    private boolean isDeleted;

}
