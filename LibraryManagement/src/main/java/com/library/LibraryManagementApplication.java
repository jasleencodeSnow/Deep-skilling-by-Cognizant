package com.library;

import com.library.service.BookService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * LibraryManagementApplication serves a dual purpose:
 *
 *   1. Acts as the Spring Boot entry point (Exercise 9)
 *      — starts the embedded Tomcat and exposes REST endpoints.
 *
 *   2. Demonstrates Exercises 1-8 via the XML ApplicationContext
 *      — loads applicationContext.xml, retrieves the BookService bean,
 *        and calls its methods so AOP advice fires in the console.
 */
@SpringBootApplication
public class LibraryManagementApplication {

    public static void main(String[] args) {

        // -------------------------------------------------------
        // Exercise 1, 2, 3, 5, 6, 7, 8:
        // Load the XML application context and exercise the beans
        // -------------------------------------------------------
        System.out.println("======================================================");
        System.out.println(" EXERCISES 1-8: Spring Core + AOP (XML Context)");
        System.out.println("======================================================");

        try (ApplicationContext xmlCtx =
                     new ClassPathXmlApplicationContext("applicationContext.xml")) {

            // Exercise 1 & 5: Retrieve BookService bean from context
            // Exercise 6: Works equally with annotation-based (@Service) bean
            // Exercise 7: Using setter-injected bean
            BookService bookService = (BookService) xmlCtx.getBean("bookServiceSetter");

            System.out.println("\n--- getAllBooks() ---");
            // Exercise 3 & 8: AOP advice fires around this call
            List<String> books = bookService.getAllBooks();
            books.forEach(b -> System.out.println("  " + b));

            System.out.println("\n--- addBook() ---");
            bookService.addBook("Spring in Action by Craig Walls");

            System.out.println("\n--- searchBooks('java') ---");
            List<String> results = bookService.searchBooks("java");
            results.forEach(b -> System.out.println("  Found: " + b));

            System.out.println("\n--- Constructor-injected bean ---");
            // Exercise 7: constructor injection
            BookService bookServiceCtor = (BookService) xmlCtx.getBean("bookServiceConstructor");
            System.out.println("  Book count: " + bookServiceCtor.getAllBooks().size());
        }

        System.out.println("\n======================================================");
        System.out.println(" EXERCISE 9: Spring Boot REST Application starting...");
        System.out.println(" REST API available at http://localhost:8080/api/books");
        System.out.println("======================================================\n");

        // Exercise 9: Start Spring Boot (embeds Tomcat, serves REST endpoints)
        SpringApplication.run(LibraryManagementApplication.class, args);
    }
}
