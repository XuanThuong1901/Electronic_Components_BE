package com.poly.ecommercestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

//@Data
@Getter
@Setter
@Entity
@Table(name = "Specifications")
public class Specifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDSpecification")
    private int iDSpecification;

    @Column(name = "SpecificationName")
    private String specificationName;

    @Column(name = "Parameter")
    private String parameter;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "IDProduct")
    private Products product;

    public Specifications() {
    }

    public Specifications(String specificationName, String parameter) {
        this.specificationName = specificationName;
        this.parameter = parameter;
    }

    public Specifications(String specificationName, String parameter, Products product) {
        this.specificationName = specificationName;
        this.parameter = parameter;
        this.product = product;
    }
}
