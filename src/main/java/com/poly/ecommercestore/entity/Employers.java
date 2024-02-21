package com.poly.ecommercestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

//@Data
@Getter
@Setter
@Entity
@Table(name = "Employers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employers {

    @Id
    @Column(name = "IDEmployer")
    private String iDEmployer;

    @MapsId
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "IDEmployer")
    private Accounts account;

    @Column(name = "Name")
    private String name;

    @Column(name = "Address")
    private String address;

    @Column(name = "Birthday")
    private Date birthday;

    @Column(name = "Gender")
    private Boolean gender;

    @Column(name = "Telephone")
    private String telephone;

    @Column(name = "IdentityCard")
    private String identityCard;

    @Column(name = "Avatar")
    private String avatar;

    @JsonIgnore()
    @OneToMany(mappedBy = "employer", fetch = FetchType.LAZY)
    private List<Orders> orders;

    @JsonIgnore()
    @OneToMany(mappedBy = "employer", fetch = FetchType.LAZY)
    private List<ImportStocks> importStocks;

    @JsonIgnore()
    @OneToMany(mappedBy = "employer", fetch = FetchType.LAZY)
    private List<PriceLists> priceLists;

    @JsonIgnore()
    @OneToMany(mappedBy = "employer", fetch = FetchType.LAZY)
    private List<ReturnOrder> returnOrderList;

    public Employers() {
    }

    public Employers(String iDEmployer, Accounts account,String name, String address, Date birthday, Boolean gender, String telephone, String identityCard, String avatar) {
        this.iDEmployer = iDEmployer;
        this.account = account;
        this.name = name;
        this.address = address;
        this.birthday = birthday;
        this.gender = gender;
        this.telephone = telephone;
        this.identityCard = identityCard;
        this.avatar = avatar;
    }

    public Employers(String iDEmployer, Accounts account, String name, String address, Date birthday, Boolean gender, String telephone, String identityCard, String avatar, List<Orders> orders, List<ImportStocks> importStocks, List<ReturnOrder> returnOrderList) {
        this.iDEmployer = iDEmployer;
        this.account = account;
        this.name = name;
        this.address = address;
        this.birthday = birthday;
        this.gender = gender;
        this.telephone = telephone;
        this.identityCard = identityCard;
        this.avatar = avatar;
        this.orders = orders;
        this.importStocks = importStocks;
        this.returnOrderList = returnOrderList;
    }
}
