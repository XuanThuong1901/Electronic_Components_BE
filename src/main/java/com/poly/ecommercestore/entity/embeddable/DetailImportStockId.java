package com.poly.ecommercestore.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@Embeddable
public class DetailImportStockId implements Serializable {

    @Column(name = "IDImportStock")
    private int iDImportStock;

    @Column(name = "IDProduct")
    private int iDProduct;

    public DetailImportStockId() {
    }

    public DetailImportStockId(int iDImportStock, int iDProduct) {
        this.iDImportStock = iDImportStock;
        this.iDProduct = iDProduct;
    }


}
