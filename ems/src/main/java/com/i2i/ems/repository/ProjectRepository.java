package com.i2i.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.i2i.ems.model.Project;

/**
 * Repository interface for performing CRUD operations on the Project entity.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Project findByProjectIdAndIsDeletedFalse(int id);
}
