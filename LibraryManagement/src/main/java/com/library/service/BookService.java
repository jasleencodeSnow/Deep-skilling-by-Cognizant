package com.library.service;

import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Exercise 1 & 5: BookService defined as a Spring-managed bean.
 *
 * Exercise 6: @Service annotation — Spring auto-detects this class
 *             during component scanning (no XML bean entry needed).
 *
 * Exercise 7: Supports BOTH constructor injection and setter injection
 *             so applicationContext.xml can demonstrate either approach.
 */
@Service
public class BookService {

    private BookRepository bookRepository;

    // -------------------------------------------------------
    // Exercise 7: Constructor Injection
    // -------------------------------------------------------
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("[BookService] Constructor injection used.");
    }

    // -------------------------------------------------------
    // No-arg constructor required when using setter injection via XML
    // -------------------------------------------------------
    public BookService() {
        System.out.println("[BookService] No-arg constructor called (for setter injection).");
    }

    // -------------------------------------------------------
    // Exercise 2 & 5 & 7: Setter Injection
    // -------------------------------------------------------
    @Autowired   // Also satisfies Exercise 6 annotation-based wiring
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("[BookService] Setter injection used.");
    }

    // -------------------------------------------------------
    // Business methods (used by AOP pointcuts in Exercises 3 & 8)
    // -------------------------------------------------------

    /** Returns all books. */
    public List<String> getAllBooks() {
        System.out.println("[BookService] getAllBooks() called.");
        return bookRepository.findAllBooks();
    }

    /** Adds a new book. */
    public void addBook(String title) {
        System.out.println("[BookService] addBook() called with: " + title);
        bookRepository.addBook(title);
    }

    /** Searches books by title keyword. */
    public List<String> searchBooks(String keyword) {
        System.out.println("[BookService] searchBooks() called with keyword: " + keyword);
        return bookRepository.findByTitle(keyword);
    }
}
