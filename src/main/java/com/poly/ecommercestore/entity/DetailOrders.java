package com.poly.ecommercestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poly.ecommercestore.entity.embeddable.DetailOrderId;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;

//@Data
@Getter
@Setter
@Entity
@Table(name = "DetailOrders")
@JsonIgnoreProperties("detailOrder")
public class DetailOrders {

    @EmbeddedId
    private DetailOrderId detailOrderId;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "LineAmount")
    private BigDecimal lineAmount;

    @Column(name = "check_return_order")
    private boolean checkReturnOrder;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IDOrder", referencedColumnName = "IDOrder", insertable = false, updatable = false)
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IDProduct", referencedColumnName = "IDProduct", insertable = false, updatable = false)
    private Products product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IDTax")
    private Tax tax;

    public DetailOrders() {
    }

    public DetailOrders(DetailOrderId detailOrderId, int quantity, BigDecimal price, BigDecimal lineAmount, Orders order, Products product, Tax tax, boolean checkReturnOrder) {
        this.detailOrderId = detailOrderId;
        this.quantity = quantity;
        this.price = price;
        this.lineAmount = lineAmount;
        this.order = order;
        this.product = product;
        this.tax = tax;
        this.checkReturnOrder = checkReturnOrder;
    }
}
