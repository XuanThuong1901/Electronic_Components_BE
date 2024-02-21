package com.poly.ecommercestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poly.ecommercestore.entity.embeddable.CartId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Date;

//@Data
@Getter
@Setter
@Entity
@Table(name = "Carts")
public class Carts {

    @EmbeddedId
    private CartId cartId;

    @Column(name = "Status")
    private Boolean status;

    @Column(name = "UpdatedDate")
    private Date updatedDate;

    @Column(name = "Quantity")
    private int quantity;

    @JsonIgnoreProperties("cart")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "IDCustomer", referencedColumnName = "IDCustomer", insertable = false, updatable = false)
    private Customers customer;

    @JsonIgnoreProperties("cart")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "IDProduct", referencedColumnName = "IDProduct", insertable = false, updatable = false)
    private Products product;

    public Carts() {
    }

    public Carts(CartId cartId, Boolean status, Date updatedDate, int quantity, Customers customer, Products product) {
        this.cartId = cartId;
        this.status = status;
        this.updatedDate = updatedDate;
        this.quantity = quantity;
        this.customer = customer;
        this.product = product;
    }
}
