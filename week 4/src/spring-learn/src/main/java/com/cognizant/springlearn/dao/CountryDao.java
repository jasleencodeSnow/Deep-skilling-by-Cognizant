package com.cognizant.springlearn.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.cognizant.springlearn.model.Country;

/**
 * Hands-on 1 (Task 6) / Hands-on 2: Loads the list of countries from country.xml
 * using ClassPathXmlApplicationContext.
 */
@Repository
public class CountryDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryDao.class);

    @SuppressWarnings("unchecked")
    private static final List<Country> COUNTRY_LIST;

    static {
        LOGGER.info("Loading country list from country.xml");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        COUNTRY_LIST = (List<Country>) context.getBean("countryList", List.class);
        LOGGER.debug("Country list loaded: {}", COUNTRY_LIST);
    }

    public List<Country> getAllCountries() {
        LOGGER.info("START");
        LOGGER.debug("Returning {} countries", COUNTRY_LIST.size());
        LOGGER.info("END");
        return COUNTRY_LIST;
    }
}
