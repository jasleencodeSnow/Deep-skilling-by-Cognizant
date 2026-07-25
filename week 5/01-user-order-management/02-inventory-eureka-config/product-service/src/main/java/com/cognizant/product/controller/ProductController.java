package com.cognizant.product.controller;

import com.cognizant.product.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/products")
public class ProductController {

    // In-memory store; swap for JPA/DB in a real deployment
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong idSeq = new AtomicLong(1);

    // Pulled from centralized config server (product-service.yml)
    @Value("${product.low-stock-threshold:10}")
    private int lowStockThreshold;

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        product.setId(idSeq.getAndIncrement());
        products.put(product.getId(), product);
        return product;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return List.copyOf(products.values());
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return products.get(id);
    }

    @GetMapping("/{id}/low-stock")
    public boolean isLowStock(@PathVariable Long id) {
        Product p = products.get(id);
        return p != null && p.getStock() < lowStockThreshold;
    }
}
