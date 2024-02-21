package com.poly.ecommercestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

//@Data
@Getter
@Setter
@Entity
@Table(name = "Evaluations")
public class Evaluations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDEvaluation")
    private int iDEvaluation;

    @Column(name = "Contents")
    private String contents;

    @Column(name = "evaluate")
    private int evaluate;

    @Column(name = "createDate")
    private Date createDate;

    @JsonIgnoreProperties("evaluations")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "iDCustomer")
    private Customers customer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "iDProduct")
    private Products product;

    public Evaluations() {
    }

    public Evaluations(String contents, int evaluate, Date createDate, Customers customer, Products product) {
        this.contents = contents;
        this.evaluate = evaluate;
        this.createDate = createDate;
        this.customer = customer;
        this.product = product;
    }
}
