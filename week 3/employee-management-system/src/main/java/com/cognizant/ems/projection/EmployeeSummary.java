package com.cognizant.ems.projection;

import org.springframework.beans.factory.annotation.Value;

/**
 * Doc 4 - Exercise 8: interface-based projection.
 * Spring Data JPA generates the implementation at runtime and only selects
 * the columns needed to satisfy this interface (id, name, email).
 */
public interface EmployeeSummary {

    Long getId();

    String getName();

    String getEmail();

    // @Value-based projection: a computed/derived property built from other
    // properties of the underlying Employee ("target").
    @Value("#{target.name + ' <' + target.email + '>'}")
    String getDisplayLabel();
}
