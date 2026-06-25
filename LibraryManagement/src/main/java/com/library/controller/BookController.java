package com.library.controller;

import com.library.entity.Book;
import com.library.repository.BookJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Exercise 9: REST Controller exposing CRUD endpoints for Book management.
 *
 * Endpoints:
 *   GET    /api/books           - list all books
 *   GET    /api/books/{id}      - get book by id
 *   GET    /api/books/search?keyword=X  - search by title keyword
 *   POST   /api/books           - create a new book
 *   PUT    /api/books/{id}      - update an existing book
 *   DELETE /api/books/{id}      - delete a book
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookJpaRepository bookJpaRepository;

    @Autowired
    public BookController(BookJpaRepository bookJpaRepository) {
        this.bookJpaRepository = bookJpaRepository;
    }

    // -------------------------------------------------------
    // GET /api/books  — retrieve all books
    // -------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookJpaRepository.findAll();
        return ResponseEntity.ok(books);
    }

    // -------------------------------------------------------
    // GET /api/books/{id}  — retrieve a single book
    // -------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookJpaRepository.findById(id);
        return book.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // -------------------------------------------------------
    // GET /api/books/search?keyword=Java  — search by title
    // -------------------------------------------------------
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String keyword) {
        List<Book> results = bookJpaRepository.findByTitleContainingIgnoreCase(keyword);
        return ResponseEntity.ok(results);
    }

    // -------------------------------------------------------
    // POST /api/books  — create a new book
    // -------------------------------------------------------
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book saved = bookJpaRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // -------------------------------------------------------
    // PUT /api/books/{id}  — update an existing book
    // -------------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,
                                           @RequestBody Book updatedBook) {
        return bookJpaRepository.findById(id).map(existing -> {
            existing.setTitle(updatedBook.getTitle());
            existing.setAuthor(updatedBook.getAuthor());
            existing.setIsbn(updatedBook.getIsbn());
            Book saved = bookJpaRepository.save(existing);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    // -------------------------------------------------------
    // DELETE /api/books/{id}  — delete a book
    // -------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!bookJpaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookJpaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
