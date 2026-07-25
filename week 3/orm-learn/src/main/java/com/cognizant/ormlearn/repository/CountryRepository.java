package com.cognizant.ormlearn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.ormlearn.model.Country;

/**
 * Doc 1 - Hands on 1: basic JpaRepository CRUD.
 * Doc 2 - Hands on 1: Query Methods for searching countries.
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

    // Search box: countries whose name contains the typed characters (e.g. "ou")
    List<Country> findByNameContaining(String text);

    // Same search, but returned in ascending order of name
    List<Country> findByNameContainingOrderByNameAsc(String text);

    // Alphabet index: countries whose name starts with the chosen letter
    List<Country> findByNameStartingWith(String letter);
}
