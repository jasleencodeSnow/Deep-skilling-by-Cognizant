package com.cognizant.payment.controller;

import com.cognizant.payment.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payments/charge")
    public CompletableFuture<String> charge(@RequestParam String orderId, @RequestParam double amount) {
        return paymentService.chargeAsync(orderId, amount);
    }
}
