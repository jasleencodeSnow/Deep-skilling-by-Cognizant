package com.cognizant.gateway.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Configuration
public class CachingConfig {

    // Simple response cache manager (swap for Redis/Caffeine in production)
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("gatewayResponses");
    }

    // Adds a Cache-Control header so downstream/browser caches can cache GET
    // responses routed through the gateway, satisfying the "caching" requirement.
    @Bean
    public WebFilter cacheControlHeaderFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            if (exchange.getRequest().getMethod() != null
                    && exchange.getRequest().getMethod().name().equals("GET")) {
                exchange.getResponse().getHeaders()
                        .add(HttpHeaders.CACHE_CONTROL, "max-age=" + Duration.ofSeconds(30).getSeconds());
            }
            return chain.filter(exchange);
        };
    }
}
