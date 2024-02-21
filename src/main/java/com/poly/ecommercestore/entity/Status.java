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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "Status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDStatus")
    private int iDStatus;

    @Column(name = "StatusName")
    private String statusName;

    @Column(name = "Type")
    private String type;

    @JsonIgnore
    @OneToMany(mappedBy = "status")
    private List<Accounts> accounts;

    @JsonIgnore
    @OneToMany(mappedBy = "status")
    private List<ImportStocks> importStocks;

    @JsonIgnore
    @OneToMany(mappedBy = "status")
    private List<Orders> orders;

    public Status() {
    }

    public Status(String statusName, String type) {
        this.statusName = statusName;
        this.type = type;
    }

    public Status(String statusName, String type, List<Accounts> accounts, List<ImportStocks> importStocks, List<Orders> orders) {
        this.statusName = statusName;
        this.type = type;
        this.accounts = accounts;
        this.importStocks = importStocks;
        this.orders = orders;
    }
}
