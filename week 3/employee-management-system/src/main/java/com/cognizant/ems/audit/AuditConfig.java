package com.cognizant.ems.audit;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

/**
 * Doc 4 - Exercise 7: supplies the value used to populate @CreatedBy /
 * @LastModifiedBy. In a real application this would read the authenticated
 * user (e.g. from Spring Security's SecurityContext); this demo returns a
 * fixed system user since the project has no security layer configured.
 */
@Configuration
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("system");
    }
}
