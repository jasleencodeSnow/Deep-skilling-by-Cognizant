package com.cognizant.ems.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.ems.model.Department;
import com.cognizant.ems.model.Employee;
import com.cognizant.ems.projection.EmployeeNameOnly;
import com.cognizant.ems.projection.EmployeeSummary;
import com.cognizant.ems.repository.DepartmentRepository;
import com.cognizant.ems.repository.EmployeeRepository;

/**
 * Doc 4 - Exercise 4: CRUD operations for Employee.
 * Doc 4 - Exercise 5: derived/custom/named query methods.
 * Doc 4 - Exercise 6: pagination and sorting.
 * Doc 4 - Exercise 8: projections.
 * Doc 4 - Exercise 10: batch processing with Hibernate.
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional
    public Employee create(Employee employee, Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NoSuchElementException("Department not found: " + departmentId));
        employee.setDepartment(department);
        return employeeRepository.save(employee);
    }

    @Transactional
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Transactional
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found: " + id));
    }

    @Transactional
    public Employee update(Long id, Employee updated) {
        Employee existing = findById(id);
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        return employeeRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    // ---- Exercise 5: derived / @Query / Named Query methods ----------------
    @Transactional
    public List<Employee> searchByName(String namePart) {
        return employeeRepository.findByNameContainingIgnoreCase(namePart);
    }

    @Transactional
    public List<Employee> searchByDepartmentId(Long departmentId) {
        return employeeRepository.searchByDepartment(departmentId);
    }

    @Transactional
    public List<Employee> searchByDepartmentName(String departmentName) {
        return employeeRepository.findByDepartmentName(departmentName);
    }

    @Transactional
    public List<Employee> searchByEmailDomain(String domain) {
        return employeeRepository.findByEmailDomain(domain);
    }

    // ---- Exercise 6: pagination and sorting --------------------------------
    @Transactional
    public Page<Employee> search(String namePart, Pageable pageable) {
        if (namePart == null || namePart.isBlank()) {
            return employeeRepository.findAll(pageable);
        }
        return employeeRepository.findByNameContainingIgnoreCase(namePart, pageable);
    }

    // ---- Exercise 8: projections --------------------------------------------
    @Transactional
    public List<EmployeeSummary> summariesByDepartment(String departmentName) {
        return employeeRepository.findByDepartment_Name(departmentName);
    }

    @Transactional
    public List<EmployeeNameOnly> allNamesOnly() {
        return employeeRepository.findAllNamesOnly();
    }

    // ---- Exercise 10: batch processing with Hibernate -----------------------
    // hibernate.jdbc.batch_size (see application.properties) groups these
    // inserts into batches instead of issuing one round-trip per row.
    @Transactional
    public List<Employee> createInBulk(List<Employee> employees, Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NoSuchElementException("Department not found: " + departmentId));
        employees.forEach(e -> e.setDepartment(department));
        return employeeRepository.saveAll(employees);
    }
}
