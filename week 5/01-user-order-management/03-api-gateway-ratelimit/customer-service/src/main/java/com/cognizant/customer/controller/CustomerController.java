package com.cognizant.customer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @GetMapping("/{id}")
    public Map<String, Object> getCustomer(@PathVariable String id) {
        return Map.of("id", id, "name", "Customer " + id, "status", "ACTIVE");
    }
}
