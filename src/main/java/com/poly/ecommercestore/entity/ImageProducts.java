package com.poly.ecommercestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

//@Data
@Getter
@Setter
@Entity
@Table(name = "ImageProducts")
public class ImageProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDImgProduct")
    private int iDImgProduct;

    @Column(name = "URLImg")
    private String uRLImg;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "IDProduct")
    private Products product;

    public ImageProducts() {
    }

    public ImageProducts(String uRLImg, Products product) {
        this.uRLImg = uRLImg;
        this.product = product;
    }
}
