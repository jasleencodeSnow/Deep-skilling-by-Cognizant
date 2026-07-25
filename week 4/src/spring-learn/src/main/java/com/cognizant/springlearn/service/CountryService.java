package com.cognizant.springlearn.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.springlearn.dao.CountryDao;
import com.cognizant.springlearn.model.Country;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;

@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    @Autowired
    private CountryDao countryDao;

    @Transactional
    public List<Country> getAllCountries() {
        LOGGER.info("START");
        List<Country> countries = countryDao.getAllCountries();
        LOGGER.info("END");
        return countries;
    }

    /**
     * Hands-on 2: Get country based on country code - case insensitive match.
     * Implemented using a lambda / stream expression as suggested in the hands-on.
     */
    @Transactional
    public Country getCountry(String code) throws CountryNotFoundException {
        LOGGER.info("START");
        LOGGER.debug("Looking up country with code {}", code);
        Country country = countryDao.getAllCountries().stream()
                .filter(c -> c.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new CountryNotFoundException("Country not found"));
        LOGGER.info("END");
        return country;
    }

    @Transactional
    public Country addCountry(Country country) {
        LOGGER.info("START");
        countryDao.getAllCountries().add(country);
        LOGGER.info("END");
        return country;
    }

    @Transactional
    public Country updateCountry(Country country) throws CountryNotFoundException {
        LOGGER.info("START");
        List<Country> countries = countryDao.getAllCountries();
        for (int i = 0; i < countries.size(); i++) {
            if (countries.get(i).getCode().equalsIgnoreCase(country.getCode())) {
                countries.set(i, country);
                LOGGER.info("END");
                return country;
            }
        }
        throw new CountryNotFoundException("Country not found");
    }

    @Transactional
    public void deleteCountry(String code) throws CountryNotFoundException {
        LOGGER.info("START");
        boolean removed = countryDao.getAllCountries().removeIf(c -> c.getCode().equalsIgnoreCase(code));
        if (!removed) {
            throw new CountryNotFoundException("Country not found");
        }
        LOGGER.info("END");
    }
}
