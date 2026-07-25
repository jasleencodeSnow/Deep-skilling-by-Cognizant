package com.cognizant.payment.client;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

// Simulates a slow / occasionally-failing third-party payment gateway.
@Component
public class ThirdPartyPaymentClient {

    public String charge(String orderId, double amount) {
        try {
            // Simulate latency: sometimes fast, sometimes very slow (>2s -> "slow call")
            int delayMs = ThreadLocalRandom.current().nextInt(200, 4000);
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Simulate a ~30% failure rate
        if (ThreadLocalRandom.current().nextInt(100) < 30) {
            throw new RuntimeException("Third-party payment gateway error for order " + orderId);
        }
        return "SUCCESS:" + orderId + ":" + amount;
    }
}
