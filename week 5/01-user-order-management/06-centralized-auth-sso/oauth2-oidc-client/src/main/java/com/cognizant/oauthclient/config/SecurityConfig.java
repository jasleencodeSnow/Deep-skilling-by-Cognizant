package com.cognizant.oauthclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// Modern (Spring Security 6 / Spring Boot 3) replacement for the deprecated
// WebSecurityConfigurerAdapter approach: expose a SecurityFilterChain bean.
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> {}); // delegates to the OIDC/OAuth2 provider configured above
        return http.build();
    }
}
