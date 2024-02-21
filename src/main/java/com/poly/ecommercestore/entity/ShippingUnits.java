package com.poly.ecommercestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.util.List;

//@Data
@Getter
@Setter
@Entity
@Table(name = "ShippingUnits")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class ShippingUnits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDShippingUnit")
    private int iDShippingUnit;

    @Column(name = "ShippingUnitName")
    private String shippingUnitName;

    @Column(name = "Email", unique = true)
    private String email;

    @Column(name = "Telephone", unique = true)
    private String telephone;

    @Column(name = "Address", unique = true)
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "shippingUnit")
    private List<Orders> orders;

    public ShippingUnits() {
    }

    public ShippingUnits(String shippingUnitName, String email, String telephone, String address) {
        this.shippingUnitName = shippingUnitName;
        this.email = email;
        this.telephone = telephone;
        this.address = address;
    }

    public ShippingUnits(String shippingUnitName, String email, String telephone, String address, List<Orders> orders) {
        this.shippingUnitName = shippingUnitName;
        this.email = email;
        this.telephone = telephone;
        this.address = address;
        this.orders = orders;
    }
}
