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
@Table(name = "Payments")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPayment")
    private int iDPayment;

    @Column(name = "PaymentName", unique = true)
    private String paymentName;

    @Column(name = "Contents")
    private String contents;

    @JsonIgnore
    @OneToMany(mappedBy = "payment")
    private List<Orders> orders;

    public Payments() {
    }

    public Payments(String paymentName, String contents) {
        this.paymentName = paymentName;
        this.contents = contents;
    }

    public Payments(String paymentName, String contents, List<Orders> orders) {
        this.paymentName = paymentName;
        this.contents = contents;
        this.orders = orders;
    }
}
