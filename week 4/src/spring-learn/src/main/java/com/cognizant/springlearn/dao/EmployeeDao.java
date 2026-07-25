package com.cognizant.springlearn.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.cognizant.springlearn.model.Employee;
import com.cognizant.springlearn.service.exception.EmployeeNotFoundException;

/**
 * Hands-on 3: Reads the static employee list from employee.xml.
 * Hands-on 4: updateEmployee() / deleteEmployee() mutate this in-memory list.
 */
@Repository
public class EmployeeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDao.class);

    private static ArrayList<Employee> EMPLOYEE_LIST;

    public EmployeeDao() {
        LOGGER.info("Loading employee list from employee.xml");
        ApplicationContext context = new ClassPathXmlApplicationContext("employee.xml");
        @SuppressWarnings("unchecked")
        List<Employee> loaded = (List<Employee>) context.getBean("employeeList", List.class);
        EMPLOYEE_LIST = new ArrayList<>(loaded);
        LOGGER.debug("Employee list loaded: {}", EMPLOYEE_LIST);
    }

    public List<Employee> getAllEmployees() {
        LOGGER.info("START");
        LOGGER.debug("Returning {} employees", EMPLOYEE_LIST.size());
        LOGGER.info("END");
        return EMPLOYEE_LIST;
    }

    public void updateEmployee(Employee employee) throws EmployeeNotFoundException {
        LOGGER.info("START");
        for (int i = 0; i < EMPLOYEE_LIST.size(); i++) {
            if (EMPLOYEE_LIST.get(i).getId().equals(employee.getId())) {
                EMPLOYEE_LIST.set(i, employee);
                LOGGER.info("END");
                return;
            }
        }
        LOGGER.debug("Employee with id {} not found", employee.getId());
        throw new EmployeeNotFoundException("Employee not found for id " + employee.getId());
    }

    public void deleteEmployee(Long id) throws EmployeeNotFoundException {
        LOGGER.info("START");
        boolean removed = EMPLOYEE_LIST.removeIf(e -> e.getId().equals(id));
        if (!removed) {
            LOGGER.debug("Employee with id {} not found", id);
            throw new EmployeeNotFoundException("Employee not found for id " + id);
        }
        LOGGER.info("END");
    }
}
