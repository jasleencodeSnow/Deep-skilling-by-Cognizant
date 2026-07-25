package com.cognizant.ormlearn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognizant.ormlearn.model.Employee;

/**
 * Doc 2 - Hands on 4/5/6: Employee CRUD + relationships.
 * Doc 3 - Hands on 2: HQL to get all permanent employees (with fetch joins).
 * Doc 3 - Hands on 4: average salary using HQL (with and without a parameter).
 * Doc 3 - Hands on 5: get all employees using a Native Query.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Doc 3 - Hands on 2: "fetch" pulls department + skillList in a single query,
    // avoiding the extra selects / LazyInitializationException that plain "join" causes.
    @Query(value = "SELECT e FROM Employee e left join fetch e.department d "
            + "left join fetch e.skillList WHERE e.permanent = true")
    List<Employee> getAllPermanentEmployees();

    // Doc 3 - Hands on 4: average salary across all employees
    @Query(value = "SELECT AVG(e.salary) FROM Employee e")
    double getAverageSalary();

    // Doc 3 - Hands on 4: average salary filtered by department id
    @Query(value = "SELECT AVG(e.salary) FROM Employee e where e.department.id = :id")
    double getAverageSalary(@Param("id") int id);

    // Doc 3 - Hands on 5: Native Query equivalent of findAll()
    @Query(value = "SELECT * FROM employee", nativeQuery = true)
    List<Employee> getAllEmployeesNative();
}
