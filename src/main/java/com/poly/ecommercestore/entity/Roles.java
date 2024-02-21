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
@Table(name = "Roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDRole")
    private int iDRole;

    @Column(name = "Role")
    private String role;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<Accounts> accounts;

    public Roles() {
    }

    public Roles(String role) {
        this.role = role;
    }

    public Roles(String role, List<Accounts> accounts) {
        this.role = role;
        this.accounts = accounts;
    }
}
