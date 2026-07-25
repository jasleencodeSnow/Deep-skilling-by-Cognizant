package com.cognizant.ems.projection;

/**
 * Doc 4 - Exercise 8: class-based projection, populated via a JPQL
 * constructor expression: "SELECT new ...EmployeeNameOnly(e.id, e.name) FROM Employee e".
 * Unlike the interface-based projection, this is a concrete DTO class.
 */
public class EmployeeNameOnly {

    private final Long id;
    private final String name;

    public EmployeeNameOnly(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "EmployeeNameOnly [id=" + id + ", name=" + name + "]";
    }
}
