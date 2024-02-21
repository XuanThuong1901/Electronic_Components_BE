package com.poly.ecommercestore.model.request;

import lombok.Data;

@Data
public class CartRequest {

    private Integer quantity;

    public CartRequest() {
    }

    public CartRequest(Integer quantity) {
        this.quantity = quantity;
    }
}
