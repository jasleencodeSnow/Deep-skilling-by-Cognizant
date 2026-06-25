package com.library.repository;

import com.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Exercise 9: Spring Data JPA repository interface.
 * Spring auto-generates the implementation at runtime.
 *
 * NOTE: This interface coexists with the simple BookRepository class
 *       (used in Exercises 1-8). Spring Boot will use this one for
 *       the REST layer (Exercise 9); the standalone class is used
 *       via applicationContext.xml for the core exercises.
 */
@Repository
public interface BookJpaRepository extends JpaRepository<Book, Long> {

    /** Find all books by a given author (Spring Data derived query). */
    List<Book> findByAuthor(String author);

    /** Find all books whose title contains the keyword (case-insensitive). */
    List<Book> findByTitleContainingIgnoreCase(String keyword);
}
