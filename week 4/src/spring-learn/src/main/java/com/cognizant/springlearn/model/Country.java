package com.cognizant.springlearn.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hands-on 1 (Task 4): Country bean loaded from Spring XML configuration (country.xml).
 * Hands-on 4: @NotNull / @Size validation added on the "code" field.
 */
public class Country {

    private static final Logger LOGGER = LoggerFactory.getLogger(Country.class);

    @NotNull
    @Size(min = 2, max = 2, message = "Country code should be 2 characters")
    private String code;

    @NotNull
    private String name;

    public Country() {
        LOGGER.debug("Inside Country Constructor.");
    }

    public Country(String code, String name) {
        LOGGER.debug("Inside Country Constructor.");
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        LOGGER.debug("getCode() invoked. code={}", code);
        return code;
    }

    public void setCode(String code) {
        LOGGER.debug("setCode() invoked. code={}", code);
        this.code = code;
    }

    public String getName() {
        LOGGER.debug("getName() invoked. name={}", name);
        return name;
    }

    public void setName(String name) {
        LOGGER.debug("setName() invoked. name={}", name);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country [code=" + code + ", name=" + name + "]";
    }
}
