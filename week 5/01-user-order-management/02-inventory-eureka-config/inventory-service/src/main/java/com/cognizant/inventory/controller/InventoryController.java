package com.cognizant.inventory.controller;

import com.cognizant.inventory.model.StockLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final Map<Long, StockLevel> stock = new ConcurrentHashMap<>();

    // Pulled from centralized config server (inventory-service.yml)
    @Value("${inventory.reorder-buffer:5}")
    private int reorderBuffer;

    @PutMapping("/{productId}")
    public StockLevel setStock(@PathVariable Long productId, @RequestParam int quantity) {
        StockLevel level = new StockLevel(productId, quantity);
        stock.put(productId, level);
        return level;
    }

    @GetMapping("/{productId}")
    public StockLevel getStock(@PathVariable Long productId) {
        return stock.getOrDefault(productId, new StockLevel(productId, 0));
    }

    @GetMapping("/{productId}/needs-reorder")
    public boolean needsReorder(@PathVariable Long productId) {
        StockLevel level = stock.get(productId);
        return level != null && level.getQuantityAvailable() <= reorderBuffer;
    }
}
