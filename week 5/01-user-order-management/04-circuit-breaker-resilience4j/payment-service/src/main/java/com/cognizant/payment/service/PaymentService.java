package com.cognizant.payment.service;

import com.cognizant.payment.client.ThirdPartyPaymentClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final ThirdPartyPaymentClient client;

    public PaymentService(ThirdPartyPaymentClient client) {
        this.client = client;
    }

    // TimeLimiter requires the guarded method to return a CompletableFuture.
    @CircuitBreaker(name = "thirdPartyPaymentApi", fallbackMethod = "fallbackCharge")
    @TimeLimiter(name = "thirdPartyPaymentApi")
    public CompletableFuture<String> chargeAsync(String orderId, double amount) {
        return CompletableFuture.supplyAsync(
                () -> client.charge(orderId, amount),
                Executors.newVirtualThreadPerTaskExecutor()
        );
    }

    // Fallback signature must mirror the original method + a Throwable last argument.
    private CompletableFuture<String> fallbackCharge(String orderId, double amount, Throwable t) {
        // This is where fallback events get logged/monitored, per the requirement.
        log.warn("Circuit breaker fallback triggered for order {} amount {}: {}",
                orderId, amount, t.toString());
        return CompletableFuture.completedFuture(
                "FALLBACK:" + orderId + ":payment-queued-for-retry");
    }
}
