package com.i2i.ems.repository;

import jakarta.validation.constraints.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.i2i.ems.model.Employee;

/**
 * Repository interface for performing CRUD operations on the Employee entity.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Page<Employee> findAllByIsDeletedFalse(Pageable pageable);
    Employee findByIdAndIsDeletedFalse(int id);
    Employee findByEmail(String username);

    boolean existsByEmailAndIsDeletedFalse(@Email String email);
}


