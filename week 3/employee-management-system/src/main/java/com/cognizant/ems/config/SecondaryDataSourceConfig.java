package com.cognizant.ems.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Doc 4 - Exercise 9: Customizing Data Source Configuration / managing
 * multiple data sources.
 *
 * The primary datasource (used by JPA/Hibernate for Employee & Department)
 * is auto-configured by Spring Boot from spring.datasource.* in
 * application.properties (Exercise 1).
 *
 * This class shows how a *second*, independently configured datasource is
 * externalized under its own "app.datasource.audit.*" prefix and exposed as
 * a plain javax.sql.DataSource bean - e.g. for writing to a separate audit
 * database with plain JDBC, without disturbing the primary JPA datasource.
 * It is intentionally NOT marked @Primary and is not used by any
 * @Entity/repository in this project; it exists purely to demonstrate the
 * multiple-datasource configuration pattern asked for in the exercise.
 */
@Configuration
public class SecondaryDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "app.datasource.audit")
    public DataSource auditDataSource() {
        return DataSourceBuilder.create().build();
    }
}
