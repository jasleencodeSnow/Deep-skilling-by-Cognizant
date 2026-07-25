package com.cognizant.billing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    @GetMapping("/{customerId}")
    public Map<String, Object> getBill(@PathVariable String customerId) {
        return Map.of("customerId", customerId, "amountDue", 1499.00, "dueDate", "2026-08-10");
    }
}
