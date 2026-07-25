package com.cognizant.ormlearn.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.repository.CountryRepository;
import com.cognizant.ormlearn.service.exception.CountryNotFoundException;

/**
 * Doc 1 - Hands on 1: getAllCountries().
 * Doc 1 - Hands on 6: findCountryByCode() - throws CountryNotFoundException.
 * Doc 1 - Hands on 7: addCountry().
 * Doc 1 - Hands on 8: updateCountry().
 * Doc 1 - Hands on 9: deleteCountry().
 * Doc 2 - Hands on 1: Query Methods - partial name search, sorted search, starting letter search.
 */
@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Transactional
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Transactional
    public Country findCountryByCode(String countryCode) throws CountryNotFoundException {
        Optional<Country> result = countryRepository.findById(countryCode);
        if (!result.isPresent()) {
            throw new CountryNotFoundException("Country not found for code: " + countryCode);
        }
        Country country = result.get();
        return country;
    }

    @Transactional
    public void addCountry(Country country) {
        countryRepository.save(country);
    }

    @Transactional
    public void updateCountry(String code, String name) throws CountryNotFoundException {
        Optional<Country> result = countryRepository.findById(code);
        if (!result.isPresent()) {
            throw new CountryNotFoundException("Country not found for code: " + code);
        }
        Country country = result.get();
        country.setName(name);
        countryRepository.save(country);
    }

    @Transactional
    public void deleteCountry(String code) {
        countryRepository.deleteById(code);
    }

    // Doc 2 - Hands on 1: search box - countries whose name contains the typed text
    @Transactional
    public List<Country> searchByPartialName(String text) {
        return countryRepository.findByNameContaining(text);
    }

    // Doc 2 - Hands on 1: same search, ascending order of name
    @Transactional
    public List<Country> searchByPartialNameSorted(String text) {
        return countryRepository.findByNameContainingOrderByNameAsc(text);
    }

    // Doc 2 - Hands on 1: alphabet index - countries starting with a chosen letter
    @Transactional
    public List<Country> searchByStartingLetter(String letter) {
        return countryRepository.findByNameStartingWith(letter);
    }
}
