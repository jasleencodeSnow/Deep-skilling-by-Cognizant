package com.cognizant.ems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Doc 4 - Exercise 1: Employee Management System - Overview and Setup.
 * Doc 4 - Exercise 7: @EnableJpaAuditing turns on @CreatedDate/@LastModifiedDate/etc.
 */
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class EmployeeManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementSystemApplication.class, args);
    }
}
