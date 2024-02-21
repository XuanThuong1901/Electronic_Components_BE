package com.poly.ecommercestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

//@Data
@Getter
@Setter
@Entity
@Table(name = "Suppliers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Suppliers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDSupplier")
    private int iDSupplier;

    @Column(name = "SupplierName")
    private String supplierName;

    @Column(name = "Email", unique = true)
    private String email;

    @Column(name = "Telephone", unique = true)
    private String telephone;

    @Column(name = "Address")
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private List<ImportStocks> importStocks;

//    @JsonIgnoreProperties("supplier")
    @JsonIgnore
    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private List<Products> products;

    public Suppliers() {
    }

    public Suppliers(String supplierName, String email, String telephone, String address) {
        this.supplierName = supplierName;
        this.email = email;
        this.telephone = telephone;
        this.address = address;
    }

    public Suppliers(String supplierName, String email, String telephone, String address, List<ImportStocks> importStocks, List<Products> products) {
        this.supplierName = supplierName;
        this.email = email;
        this.telephone = telephone;
        this.address = address;
        this.importStocks = importStocks;
        this.products = products;
    }
}
