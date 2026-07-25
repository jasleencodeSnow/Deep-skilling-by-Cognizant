package com.cognizant.ormlearn;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cognizant.ormlearn.model.Attempt;
import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.model.Department;
import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.model.Skill;
import com.cognizant.ormlearn.model.Stock;
import com.cognizant.ormlearn.service.AttemptService;
import com.cognizant.ormlearn.service.CountryService;
import com.cognizant.ormlearn.service.CriteriaQueryDemo;
import com.cognizant.ormlearn.service.DepartmentService;
import com.cognizant.ormlearn.service.EmployeeService;
import com.cognizant.ormlearn.service.SkillService;
import com.cognizant.ormlearn.service.StockService;
import com.cognizant.ormlearn.service.exception.CountryNotFoundException;

/**
 * Entry point that walks through every hands-on exercise in documents 1, 2 and 3
 * (Spring Data JPA & Hibernate hands-on series), end to end, on every startup.
 *
 * Each test*() method below corresponds 1:1 to a hands-on step described in the
 * documents. They are all invoked from main() with clear log banners so the
 * complete solution set can be exercised in a single run.
 */
@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    private static CountryService countryService;
    private static StockService stockService;
    private static EmployeeService employeeService;
    private static DepartmentService departmentService;
    private static SkillService skillService;
    private static AttemptService attemptService;
    private static CriteriaQueryDemo criteriaQueryDemo;

    public static void main(String[] args) {
        LOGGER.info("Inside main");

        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);

        countryService = context.getBean(CountryService.class);
        stockService = context.getBean(StockService.class);
        employeeService = context.getBean(EmployeeService.class);
        departmentService = context.getBean(DepartmentService.class);
        skillService = context.getBean(SkillService.class);
        attemptService = context.getBean(AttemptService.class);
        criteriaQueryDemo = context.getBean(CriteriaQueryDemo.class);

        try {
            // ---- Doc 1: Hands on 1 ----
            banner("Doc1 - Hands on 1: getAllCountries()");
            testGetAllCountries();

            // ---- Doc 1: Hands on 6, 7, 8, 9 ----
            banner("Doc1 - Hands on 6: findCountryByCode()");
            testFindCountryByCode();

            banner("Doc1 - Hands on 7: addCountry()");
            testAddCountry();

            banner("Doc1 - Hands on 8: updateCountry()");
            testUpdateCountry();

            banner("Doc1 - Hands on 9: deleteCountry()");
            testDeleteCountry();

            // ---- Doc 2: Hands on 1 ----
            banner("Doc2 - Hands on 1: Query Methods on country");
            testCountryQueryMethods();

            // ---- Doc 2: Hands on 2 ----
            banner("Doc2 - Hands on 2: Query Methods on stock");
            testStockQueryMethods();

            // ---- Doc 2: Hands on 4 ----
            banner("Doc2 - Hands on 4: many-to-one Employee/Department");
            testGetEmployee();
            testAddEmployee();
            testUpdateEmployee();

            // ---- Doc 2: Hands on 5 ----
            banner("Doc2 - Hands on 5: one-to-many Department/Employee");
            testGetDepartment();

            // ---- Doc 2: Hands on 6 ----
            banner("Doc2 - Hands on 6: many-to-many Employee/Skill");
            testAddSkillToEmployee();

            // ---- Doc 3: Hands on 2 ----
            banner("Doc3 - Hands on 2: getAllPermanentEmployees() via HQL");
            testGetAllPermanentEmployees();

            // ---- Doc 3: Hands on 3 ----
            banner("Doc3 - Hands on 3: quiz attempt details via HQL");
            testGetAttemptDetail();

            // ---- Doc 3: Hands on 4 ----
            banner("Doc3 - Hands on 4: average salary via HQL");
            testGetAverageSalary();

            // ---- Doc 3: Hands on 5 ----
            banner("Doc3 - Hands on 5: getAllEmployeesNative() via Native Query");
            testGetAllEmployeesNative();

            banner("Doc3 - Hands on 6: Criteria Query - dynamic filtering");
            testCriteriaQuery();

        } catch (Exception e) {
            LOGGER.error("Demo run failed", e);
        }
    }

    private static void banner(String title) {
        LOGGER.info("==================================================================");
        LOGGER.info(title);
        LOGGER.info("==================================================================");
    }

    // =====================================================================
    // Doc 1 - Hands on 1
    // =====================================================================
    private static void testGetAllCountries() {
        LOGGER.info("Start");
        List<Country> countries = countryService.getAllCountries();
        LOGGER.debug("countries.size={}", countries.size());
        LOGGER.info("End");
    }

    // =====================================================================
    // Doc 1 - Hands on 6: Find a country based on country code
    // =====================================================================
    private static void testFindCountryByCode() {
        LOGGER.info("Start");
        try {
            Country country = countryService.findCountryByCode("IN");
            LOGGER.debug("Country:{}", country);
        } catch (CountryNotFoundException e) {
            LOGGER.error("Country not found", e);
        }
        LOGGER.info("End");
    }

    // =====================================================================
    // Doc 1 - Hands on 7: Add a new country
    // =====================================================================
    private static void testAddCountry() {
        LOGGER.info("Start");
        Country country = new Country("ZZ", "Zionesia");
        countryService.addCountry(country);
        try {
            Country added = countryService.findCountryByCode("ZZ");
            LOGGER.debug("Added country:{}", added);
        } catch (CountryNotFoundException e) {
            LOGGER.error("Country not found after add", e);
        }
        LOGGER.info("End");
    }

    // =====================================================================
    // Doc 1 - Hands on 8: Update a country based on code
    // =====================================================================
    private static void testUpdateCountry() {
        LOGGER.info("Start");
        try {
            countryService.updateCountry("ZZ", "Zionesia Renamed");
            Country updated = countryService.findCountryByCode("ZZ");
            LOGGER.debug("Updated country:{}", updated);
        } catch (CountryNotFoundException e) {
            LOGGER.error("Country not found for update", e);
        }
        LOGGER.info("End");
    }

    // =====================================================================
    // Doc 1 - Hands on 9: Delete a country based on code
    // =====================================================================
    private static void testDeleteCountry() {
        LOGGER.info("Start");
        countryService.deleteCountry("ZZ");
        try {
            countryService.findCountryByCode("ZZ");
            LOGGER.error("Country ZZ should have been deleted but was found");
        } catch (CountryNotFoundException e) {
            LOGGER.debug("Confirmed deleted: {}", e.getMessage());
        }
        LOGGER.info("End");
    }

    // =====================================================================
    // Doc 2 - Hands on 1: Query Methods on country
    // =====================================================================
    private static void testCountryQueryMethods() {
        LOGGER.info("Start");

        List<Country> containingOu = countryService.searchByPartialName("ou");
        LOGGER.debug("Countries containing 'ou': {}", containingOu);

        List<Country> containingOuSorted = countryService.searchByPartialNameSorted("ou");
        LOGGER.debug("Countries containing 'ou' (sorted): {}", containingOuSorted);

        List<Country> startingWithZ = countryService.searchByStartingLetter("Z");
        LOGGER.debug("Countries starting with 'Z': {}", startingWithZ);

        LOGGER.info("End");
    }

    // =====================================================================
    // Doc 2 - Hands on 2: Query Methods on stock
    // =====================================================================
    private static void testStockQueryMethods() {
        LOGGER.info("Start");

        List<Stock> fbSeptember = stockService.getStockByCodeAndDateRange(
                "FB", LocalDate.of(2019, 9, 1), LocalDate.of(2019, 9, 30));
        LOGGER.debug("FB Sept 2019: {}", fbSeptember);

        List<Stock> googleAbove1250 = stockService.getStockByCodeAboveClose(
                "GOOGL", new BigDecimal("1250"));
        LOGGER.debug("GOOGL close > 1250: {}", googleAbove1250);

        List<Stock> top3ByVolume = stockService.getTop3ByVolume();
        LOGGER.debug("Top 3 highest volume: {}", top3ByVolume);

        List<Stock> netflixLowest3 = stockService.getTop3LowestClose("NFLX");
        LOGGER.debug("NFLX lowest 3 close prices: {}", netflixLowest3);

        LOGGER.info("End");
    }

    // =====================================================================
    // Doc 2 - Hands on 4: Getting Employee along with Department
    // =====================================================================
    private static void testGetEmployee() {
        LOGGER.info("Start");
        Employee employee = employeeService.get(1);
        LOGGER.debug("Employee:{}", employee);
        LOGGER.debug("Department:{}", employee.getDepartment());
        LOGGER.debug("Skills:{}", employee.getSkillList());
        LOGGER.info("End");
    }

    private static void testAddEmployee() {
        LOGGER.info("Start");
        Employee employee = new Employee();
        employee.setName("Nithya Menon");
        employee.setSalary(65000);
        employee.setPermanent(true);
        employee.setDateOfBirth(LocalDate.of(1992, 6, 14));
        Department department = departmentService.get(1);
        employee.setDepartment(department);
        employeeService.save(employee);
        LOGGER.debug("Saved employee:{}", employee);
        LOGGER.info("End");
    }

    private static void testUpdateEmployee() {
        LOGGER.info("Start");
        Employee employee = employeeService.get(1);
        Department newDepartment = departmentService.get(2);
        employee.setDepartment(newDepartment);
        employeeService.save(employee);
        LOGGER.debug("Updated employee:{}", employee);
        LOGGER.info("End");
    }

    // =====================================================================
    // Doc 2 - Hands on 5: one-to-many Department -> Employee
    // =====================================================================
    private static void testGetDepartment() {
        LOGGER.info("Start");
        Department department = departmentService.get(1);
        LOGGER.debug("Department:{}", department);
        LOGGER.debug("Employees:{}", department.getEmployeeList());
        LOGGER.info("End");
    }

    // =====================================================================
    // Doc 2 - Hands on 6: many-to-many Employee <-> Skill
    // =====================================================================
    private static void testAddSkillToEmployee() {
        LOGGER.info("Start");
        Employee employee = employeeService.get(2);
        Skill skill = skillService.get(3);
        employee.getSkillList().add(skill);
        employeeService.save(employee);
        LOGGER.debug("Employee skills after add:{}", employee.getSkillList());
        LOGGER.info("End");
    }

    // =====================================================================
    // Doc 3 - Hands on 2: Get all permanent employees using HQL
    // =====================================================================
    public static void testGetAllPermanentEmployees() {
        LOGGER.info("Start");
        List<Employee> employees = employeeService.getAllPermanentEmployees();
        LOGGER.debug("Permanent Employees:{}", employees);
        employees.forEach(e -> LOGGER.debug("Skills:{}", e.getSkillList()));
        LOGGER.info("End");
    }

    // =====================================================================
    // Doc 3 - Hands on 3: Fetch quiz attempt details using HQL
    // =====================================================================
    private static void testGetAttemptDetail() {
        LOGGER.info("Start");
        Attempt attempt = attemptService.getAttempt(1, 1);
        attempt.getAttemptQuestionList().forEach(aq -> {
            LOGGER.debug(aq.getQuestion().getText());
            java.util.Set<Integer> selectedOptionIds = new java.util.HashSet<>();
            aq.getAttemptOptionList().forEach(ao -> selectedOptionIds.add(ao.getOption().getId()));
            aq.getQuestion().getOptionList().forEach(option ->
                    LOGGER.debug("{}) {}\t{}\t{}",
                            option.getId(),
                            option.getText(),
                            option.getScore(),
                            selectedOptionIds.contains(option.getId())));
        });
        LOGGER.info("End");
    }

    // =====================================================================
    // Doc 3 - Hands on 4: Get average salary using HQL
    // =====================================================================
    private static void testGetAverageSalary() {
        LOGGER.info("Start");
        double overallAverage = employeeService.getAverageSalary();
        LOGGER.debug("Overall average salary:{}", overallAverage);

        double departmentAverage = employeeService.getAverageSalary(1);
        LOGGER.debug("Average salary for department 1:{}", departmentAverage);
        LOGGER.info("End");
    }

    // =====================================================================
    // Doc 3 - Hands on 5: Get all employees using Native Query
    // =====================================================================
    private static void testGetAllEmployeesNative() {
        LOGGER.info("Start");
        List<Employee> employees = employeeService.getAllEmployeesNative();
        LOGGER.debug("All employees (native query):{}", employees);
        LOGGER.info("End");
    }

    // =====================================================================
    // Doc 3 - Hands on 6: Criteria Query - only the filters actually
    // supplied are added to the WHERE clause (dynamic filtering).
    // =====================================================================
    private static void testCriteriaQuery() {
        LOGGER.info("Start");
        java.util.Map<String, String> filters = new java.util.HashMap<>();
        filters.put("nameContains", "land");
        List<Country> result = criteriaQueryDemo.search(filters);
        LOGGER.debug("Criteria Query result for nameContains='land': {}", result);
        LOGGER.info("End");
    }
}
