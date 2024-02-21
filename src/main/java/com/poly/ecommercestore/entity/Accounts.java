package com.poly.ecommercestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//@Data
@Getter
@Setter
@Entity
@Table(name = "Accounts")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Accounts implements UserDetails {
    @Id
    @Column(name = "IDAccount", nullable = false)
    private String iDAccount;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

//    @ToString.Exclude
    @JsonIgnoreProperties("account")
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Employers employer;

//    @ToString.Exclude
    @JsonIgnoreProperties("account")
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Customers customers;

    @JsonIgnoreProperties("account")
    @ManyToOne(optional = false)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "IDStatus")
    private Status status;

    @JsonIgnoreProperties("account")
    @ManyToOne(optional = false)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "IDRole")
    private Roles role;

    public Accounts() {
    }

    public Accounts(String iDAccount, String email, String password, Employers employer, Customers customers, Status status, Roles role) {
        this.iDAccount = iDAccount;
        this.email = email;
        this.password = password;
        this.employer = employer;
        this.customers = customers;
        this.status = status;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getRole()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
