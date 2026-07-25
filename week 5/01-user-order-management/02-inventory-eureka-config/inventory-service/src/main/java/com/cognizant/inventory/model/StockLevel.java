package com.cognizant.inventory.model;

public class StockLevel {
    private Long productId;
    private Integer quantityAvailable;

    public StockLevel() {}

    public StockLevel(Long productId, Integer quantityAvailable) {
        this.productId = productId;
        this.quantityAvailable = quantityAvailable;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Integer getQuantityAvailable() { return quantityAvailable; }
    public void setQuantityAvailable(Integer quantityAvailable) { this.quantityAvailable = quantityAvailable; }
}
