package com.cognizant.ems.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.ems.model.Department;
import com.cognizant.ems.repository.DepartmentRepository;

/** Doc 4 - Exercise 3/4: CRUD operations for Department. */
@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional
    public Department create(Department department) {
        return departmentRepository.save(department);
    }

    @Transactional
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Transactional
    public Department findById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Department not found: " + id));
    }

    @Transactional
    public Department update(Long id, Department updated) {
        Department existing = findById(id);
        existing.setName(updated.getName());
        return departmentRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }
}
