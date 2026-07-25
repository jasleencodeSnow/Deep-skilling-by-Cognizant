package com.cognizant.springlearn.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springlearn.model.Country;
import com.cognizant.springlearn.service.CountryService;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;

/**
 * Hands-on 2: REST - Country Web Service / Get all countries / Get country based on code /
 *             Get country exceptional scenario.
 * Hands-on 4: Refactored to follow REST resource naming guidelines (plural "/countries")
 *             and to support POST / PUT / DELETE with @Valid validation.
 */
@RestController
public class CountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private CountryService countryService;

    /**
     * Hands-on 2 (Task 2): legacy endpoint - returns India directly. Kept for fidelity
     * with the original hands-on; superseded in practice by GET /countries/{code}.
     */
    @GetMapping("/country")
    public Country getCountryIndia() throws CountryNotFoundException {
        LOGGER.info("START");
        Country country = countryService.getCountry("IN");
        LOGGER.info("END");
        return country;
    }

    /** Hands-on 2 (Task 3) / Hands-on 4: GET all countries -> GET /countries */
    @GetMapping("/countries")
    public List<Country> getAllCountries() {
        LOGGER.info("START");
        List<Country> countries = countryService.getAllCountries();
        LOGGER.info("END");
        return countries;
    }

    /** Hands-on 2 (Task 4 & 5): GET one country by code -> GET /countries/{code} */
    @GetMapping("/countries/{code}")
    public Country getCountry(@PathVariable String code) throws CountryNotFoundException {
        LOGGER.info("START");
        LOGGER.debug("code={}", code);
        Country country = countryService.getCountry(code);
        LOGGER.info("END");
        return country;
    }

    /** Hands-on 4: POST /countries - create a country, validated via @Valid */
    @PostMapping("/countries")
    public Country addCountry(@RequestBody @Valid Country country) {
        LOGGER.info("START");
        LOGGER.debug("country={}", country);
        Country saved = countryService.addCountry(country);
        LOGGER.info("END");
        return saved;
    }

    /** Hands-on 4: PUT /countries - update a country, validated via @Valid */
    @PutMapping("/countries")
    public Country updateCountry(@RequestBody @Valid Country country) throws CountryNotFoundException {
        LOGGER.info("START");
        Country updated = countryService.updateCountry(country);
        LOGGER.info("END");
        return updated;
    }

    /** Hands-on 4: DELETE /countries/{code} */
    @DeleteMapping("/countries/{code}")
    public void deleteCountry(@PathVariable String code) throws CountryNotFoundException {
        LOGGER.info("START");
        countryService.deleteCountry(code);
        LOGGER.info("END");
    }
}
