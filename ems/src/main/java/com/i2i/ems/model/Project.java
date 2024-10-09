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
 * Represents an project entity in the database.
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project")
@Getter
@Setter
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private int projectId;

    @Column(name = "project_name")
    private String name;

    @Column(name = "project_domain")
    private String domain;

    @Column(name = "project_head")
    private String head;

    @Column(name = "is_deleted")
    private boolean isDeleted;

}
