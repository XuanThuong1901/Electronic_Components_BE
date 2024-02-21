package com.poly.ecommercestore.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

//@Data
@Getter
@Setter
@Entity
@Table(name = "Categories")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCategory")
    private int iDCategory;

    @Column(name = "CategoryName")
    private String categoryName;

    @Column(name = "Level")
    private int level;

    @Column(name = "Origin")
    private int origin;

    @JsonIgnore()
    @OneToMany(mappedBy = "category")
    private List<Products> products;

    public Categories() {
    }

    public Categories(String categoryName, int level, int origin) {
        this.categoryName = categoryName;
        this.level = level;
        this.origin = origin;
    }
}
