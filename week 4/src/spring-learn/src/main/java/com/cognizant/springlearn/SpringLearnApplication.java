package com.cognizant.springlearn;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cognizant.springlearn.model.Country;

@SpringBootApplication
public class SpringLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

    public static void main(String[] args) {
        LOGGER.info("START main()");

        // Hands-on 1 (Task 2): Load SimpleDateFormat from Spring Configuration XML
        displayDate();

        // Hands-on 1 (Task 4 & 5): Load Country bean, demonstrate singleton scope
        displayCountry();

        // Hands-on 1 (Task 6): Load list of countries from Spring Configuration XML
        displayCountries();

        // Start the Spring Boot web application (REST controllers, security, etc.)
        SpringApplication.run(SpringLearnApplication.class, args);

        LOGGER.info("END main()");
    }

    /** Hands-on 1 (Task 2): reads dateFormat bean from date-format.xml and parses a date. */
    public static void displayDate() {
        LOGGER.info("START");
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("date-format.xml");
            SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);
            Date date = format.parse("31/12/2018");
            LOGGER.debug("{}", date);
        } catch (Exception e) {
            LOGGER.error("Error parsing date", e);
        }
        LOGGER.info("END");
    }

    /**
     * Hands-on 1 (Task 4 & 5): reads country bean from country.xml and demonstrates
     * that the default (singleton) scope returns the same instance on every getBean() call.
     */
    public static void displayCountry() {
        LOGGER.info("START");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        Country country = context.getBean("country", Country.class);
        Country anotherCountry = context.getBean("country", Country.class);
        LOGGER.debug("Country : {}", country.toString());
        LOGGER.debug("Same instance (singleton scope)? {}", country == anotherCountry);
        LOGGER.info("END");
    }

    /** Hands-on 1 (Task 6): reads the countryList bean from country.xml and logs it. */
    @SuppressWarnings("unchecked")
    public static void displayCountries() {
        LOGGER.info("START");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        List<Country> countries = (List<Country>) context.getBean("countryList", List.class);
        LOGGER.debug("Countries : {}", countries);
        LOGGER.info("END");
    }
}
