package com.cognizant.ems.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.ems.model.Department;

/** Doc 4 - Exercise 3: DepartmentRepository with a derived query method. */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByName(String name);

    List<Department> findByNameContainingIgnoreCase(String partialName);
}
