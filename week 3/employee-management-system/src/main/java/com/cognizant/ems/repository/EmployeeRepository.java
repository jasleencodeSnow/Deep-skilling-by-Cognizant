package com.cognizant.ems.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cognizant.ems.model.Employee;
import com.cognizant.ems.projection.EmployeeNameOnly;
import com.cognizant.ems.projection.EmployeeSummary;

/**
 * Doc 4 - Exercise 3: derived query methods.
 * Doc 4 - Exercise 5: @Query methods + the @NamedQuery definitions declared on Employee.
 * Doc 4 - Exercise 6: pagination and sorting via Page/Pageable.
 * Doc 4 - Exercise 8: interface-based and class-based projections.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // ---- Exercise 3: derived query methods ---------------------------------
    List<Employee> findByNameContainingIgnoreCase(String namePart);

    List<Employee> findByDepartment_Id(Long departmentId);

    List<Employee> findByEmailEndingWith(String domainSuffix);

    // ---- Exercise 5: custom query with @Query ------------------------------
    @Query("SELECT e FROM Employee e WHERE e.department.id = :departmentId ORDER BY e.name")
    List<Employee> searchByDepartment(@Param("departmentId") Long departmentId);

    // ---- Exercise 5: Named Queries (defined via @NamedQuery on Employee) ---
    // Spring Data JPA resolves these automatically by matching the method
    // name to the query name "Employee.<methodName>" declared on the entity.
    List<Employee> findByDepartmentName(@Param("departmentName") String departmentName);

    List<Employee> findByEmailDomain(@Param("domain") String domain);

    // ---- Exercise 6: pagination and sorting --------------------------------
    Page<Employee> findByNameContainingIgnoreCase(String namePart, Pageable pageable);

    Page<Employee> findAll(Pageable pageable);

    // ---- Exercise 8: projections --------------------------------------------
    List<EmployeeSummary> findByDepartment_Name(String departmentName);

    @Query("SELECT new com.cognizant.ems.projection.EmployeeNameOnly(e.id, e.name) FROM Employee e")
    List<EmployeeNameOnly> findAllNamesOnly();
}
