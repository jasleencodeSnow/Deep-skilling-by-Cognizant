package com.cognizant.ems.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Doc 4 - Exercise 2: Employee entity, many-to-one with Department.
 * Doc 4 - Exercise 5: Named Queries (findByDepartmentName, findByEmailDomain).
 * Doc 4 - Exercise 7: extends Auditable for created/modified tracking.
 * Doc 4 - Exercise 10: Hibernate-specific annotations.
 *   - @DynamicUpdate: only changed columns are included in the UPDATE statement.
 *   - @BatchSize: hints Hibernate to batch-fetch lazy Employee proxies together.
 */
@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "department")
@DynamicUpdate
@BatchSize(size = 20)
@NamedQueries({
        @NamedQuery(
                name = "Employee.findByDepartmentName",
                query = "SELECT e FROM Employee e WHERE e.department.name = :departmentName"),
        @NamedQuery(
                name = "Employee.findByEmailDomain",
                query = "SELECT e FROM Employee e WHERE e.email LIKE CONCAT('%@', :domain)")
})
public class Employee extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonIgnoreProperties("employees")
    private Department department;
}
