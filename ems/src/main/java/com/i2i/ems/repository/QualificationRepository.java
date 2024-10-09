package com.i2i.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.i2i.ems.model.Qualification;

/**
 * Repository interface for performing CRUD operations on the Qualification entity.
 */
@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Integer> {
    Qualification findByQualificationIdAndIsDeletedFalse(int id);
}


