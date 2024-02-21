package com.poly.ecommercestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

//@Data
@Getter
@Setter
@Entity
@Table(name = "Tax")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDTax")
    private int iDTax;

    @Column(name = "Type")
    private String type;

    @Column(name = "TaxPercentage")
    private int taxPercentage;

    @JsonIgnore()
    @OneToMany(mappedBy = "tax")
    private List<DetailOrders> detailOrders;

    @JsonIgnore()
    @OneToMany(mappedBy = "tax")
    private List<Products> products;

    public Tax() {
    }

    public Tax(String type, int taxPercentage) {
        this.type = type;
        this.taxPercentage = taxPercentage;
    }
}
