package com.i2i.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.i2i.ems.model.Address;

/**
 * Repository interface for performing CRUD operations on the Address entity.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    Address findByAddressIdAndIsDeletedFalse(int id);
    Address findByEmployeeIdAndIsDeletedFalse(int employeeId);
}
