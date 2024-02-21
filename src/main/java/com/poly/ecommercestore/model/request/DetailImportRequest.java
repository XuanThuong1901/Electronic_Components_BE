package com.poly.ecommercestore.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetailImportRequest {
    private String product;
    private int quantity;
    private BigDecimal price;

    public DetailImportRequest() {
    }

    public DetailImportRequest(String product, int quantity, BigDecimal price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
}
