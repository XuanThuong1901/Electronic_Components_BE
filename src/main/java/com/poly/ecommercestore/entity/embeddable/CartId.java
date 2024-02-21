package com.poly.ecommercestore.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@Embeddable
public class CartId implements Serializable {

    @Column(name = "IDCustomer")
    private String iDCustomer;

    @Column(name = "IDProduct")
    private int iDProduct;

    public CartId() {
    }

    public CartId(String iDCustomer, int iDProduct) {
        this.iDCustomer = iDCustomer;
        this.iDProduct = iDProduct;
    }
}
