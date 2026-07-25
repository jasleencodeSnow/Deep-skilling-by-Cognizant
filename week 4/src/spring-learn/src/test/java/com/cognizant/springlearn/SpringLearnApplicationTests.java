package com.cognizant.springlearn;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.cognizant.springlearn.controller.CountryController;

/**
 * Hands-on 2: MockMVC - Test get country service / exceptional scenario.
 *
 * NOTE: Since Hands-on 5 secures all endpoints, requests below authenticate
 * with the in-memory "user"/"pwd" credentials via httpBasic().
 */
@SpringBootTest
@AutoConfigureMockMvc
class SpringLearnApplicationTests {

    @Autowired
    private CountryController countryController;

    @Autowired
    private MockMvc mvc;

    /** Test loading CountryController */
    @Test
    void contextLoads() {
        assertNotNull(countryController);
    }

    /** Test service to get the country -> GET /countries/in */
    @Test
    void testGetCountry() throws Exception {
        ResultActions actions = mvc.perform(get("/countries/in").with(httpBasic("user", "pwd")));

        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$.code").exists());
        actions.andExpect(jsonPath("$.code").value("IN"));
        actions.andExpect(jsonPath("$.name").exists());
        actions.andExpect(jsonPath("$.name").value("India"));
    }

    /** Test get country service for exceptional scenario -> unknown code returns 404 */
    @Test
    void testGetCountryException() throws Exception {
        ResultActions actions = mvc.perform(get("/countries/az").with(httpBasic("user", "pwd")));

        actions.andExpect(status().isNotFound());
        actions.andExpect(status().reason("Country not found"));
    }

    /** GET /countries returns the full list */
    @Test
    void testGetAllCountries() throws Exception {
        ResultActions actions = mvc.perform(get("/countries").with(httpBasic("user", "pwd")));

        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$").isArray());
    }
}
