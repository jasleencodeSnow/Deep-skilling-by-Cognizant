package com.cognizant.ems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.ems.model.Employee;
import com.cognizant.ems.projection.EmployeeNameOnly;
import com.cognizant.ems.projection.EmployeeSummary;
import com.cognizant.ems.service.EmployeeService;

/**
 * Doc 4 - Exercise 4: RESTful endpoints for Employee CRUD operations.
 * Doc 4 - Exercise 5: search endpoints backed by derived/@Query/Named Query methods.
 * Doc 4 - Exercise 6: paginated + sorted search endpoint.
 * Doc 4 - Exercise 8: projection endpoints.
 * Doc 4 - Exercise 10: bulk create endpoint exercising Hibernate batch inserts.
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee, @RequestParam Long departmentId) {
        return employeeService.create(employee, departmentId);
    }

    @GetMapping
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeService.update(id, employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        employeeService.delete(id);
    }

    // ---- Exercise 5: search endpoints ---------------------------------------
    @GetMapping("/search/by-name")
    public List<Employee> searchByName(@RequestParam String name) {
        return employeeService.searchByName(name);
    }

    @GetMapping("/search/by-department/{departmentId}")
    public List<Employee> searchByDepartment(@PathVariable Long departmentId) {
        return employeeService.searchByDepartmentId(departmentId);
    }

    @GetMapping("/search/by-department-name")
    public List<Employee> searchByDepartmentName(@RequestParam String departmentName) {
        return employeeService.searchByDepartmentName(departmentName);
    }

    @GetMapping("/search/by-email-domain")
    public List<Employee> searchByEmailDomain(@RequestParam String domain) {
        return employeeService.searchByEmailDomain(domain);
    }

    // ---- Exercise 6: pagination + sorting ------------------------------------
    @GetMapping("/search")
    public Page<Employee> search(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return employeeService.search(name, pageable);
    }

    // ---- Exercise 8: projections ----------------------------------------------
    @GetMapping("/projections/summary")
    public List<EmployeeSummary> summaries(@RequestParam String departmentName) {
        return employeeService.summariesByDepartment(departmentName);
    }

    @GetMapping("/projections/names-only")
    public List<EmployeeNameOnly> namesOnly() {
        return employeeService.allNamesOnly();
    }

    // ---- Exercise 10: bulk create (Hibernate batch insert) --------------------
    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Employee> createInBulk(@RequestBody List<Employee> employees,
                                        @RequestParam Long departmentId) {
        return employeeService.createInBulk(employees, departmentId);
    }
}
