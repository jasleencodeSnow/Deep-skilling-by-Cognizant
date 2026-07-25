package com.cognizant.springlearn.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.cognizant.springlearn.model.Department;

@Repository
public class DepartmentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDao.class);

    @SuppressWarnings("unchecked")
    private static final List<Department> DEPARTMENT_LIST;

    static {
        LOGGER.info("Loading department list from department.xml");
        ApplicationContext context = new ClassPathXmlApplicationContext("department.xml");
        DEPARTMENT_LIST = (List<Department>) context.getBean("departmentList", List.class);
        LOGGER.debug("Department list loaded: {}", DEPARTMENT_LIST);
    }

    public List<Department> getAllDepartments() {
        LOGGER.info("START");
        LOGGER.debug("Returning {} departments", DEPARTMENT_LIST.size());
        LOGGER.info("END");
        return DEPARTMENT_LIST;
    }
}
