package com.library.repository;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 1 & 5: BookRepository defined as a Spring bean.
 * Exercise 6: @Repository annotation replaces XML bean definition for
 *             annotation-based configuration.
 */
@Repository
public class BookRepository {

    // In-memory list simulating a database
    private final List<String> books = new ArrayList<>();

    public BookRepository() {
        // Pre-populate some books
        books.add("Clean Code by Robert C. Martin");
        books.add("The Pragmatic Programmer by Andrew Hunt");
        books.add("Effective Java by Joshua Bloch");
    }

    /**
     * Returns all books in the repository.
     */
    public List<String> findAllBooks() {
        return new ArrayList<>(books);
    }

    /**
     * Adds a new book to the repository.
     */
    public void addBook(String title) {
        books.add(title);
        System.out.println("[BookRepository] Book added: " + title);
    }

    /**
     * Finds books whose title contains the given keyword (case-insensitive).
     */
    public List<String> findByTitle(String keyword) {
        List<String> result = new ArrayList<>();
        for (String book : books) {
            if (book.toLowerCase().contains(keyword.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }
}
