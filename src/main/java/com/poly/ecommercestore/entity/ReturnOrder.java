package com.poly.ecommercestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ReturnOrder")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ReturnOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String address;

    private String phoneNumber;

    private Integer quantity;

    private BigDecimal amount;

    private String reason;

    private String formality;

    private String status;

    private Date createdDate;

    @JsonIgnoreProperties("returnOrder")
    @OneToMany(mappedBy = "returnOrder", fetch = FetchType.LAZY)
    private List<ImageReturnOrder> imageReturnOrderList;


//    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.REMOVE},fetch = FetchType.LAZY,orphanRemoval = false)
//    @JoinColumn(name = "return_order_id")
//    private List<ImageReturnOrder> imageReturnOrderList = new ArrayList<>();

    @JsonIgnoreProperties("returnOrder")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "IdProduct")
    private Products product;

    @JsonIgnoreProperties("returnOrder")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idCustomer")
    private Customers customer;

    @JsonIgnoreProperties("returnOrder")
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "idEmployee")
    private Employers employer;

    @PrePersist
    private void pre(){
        this.createdDate = new Date();
        this.status = "unconfirmed";
    }
}
