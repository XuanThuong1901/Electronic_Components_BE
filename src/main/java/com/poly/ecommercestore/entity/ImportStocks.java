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
@Table(name = "ImportStocks")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ImportStocks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDImportStock")
    private int iDImportStock;

    @Column(name = "ImportStockName")
    private String importStockName;

    @Column(name = "Contents")
    private String contents;

    @Column(name = "DateAdded")
    private Date dateAdded;

    @Column(name = "UpdatedDate")
    private Date updatedDate;

    @Column(name = "Amount")
    private BigDecimal amount;

    @JsonIgnoreProperties("import")
    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IDEmployer")
    private Employers employer;

    @JsonIgnoreProperties("import")
    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IDSupplier")
    private Suppliers supplier;

    @ManyToOne(optional = false)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "IDStatus")
    private Status status;

    @JsonIgnoreProperties("import")
    @OneToMany(mappedBy = "importStock", fetch = FetchType.LAZY)
    private List<DetailImportStocks> detailImportStocks;

    public ImportStocks() {
    }

    public ImportStocks(int iDImportStock, String importStockName, String contents, Date dateAdded, Date updatedDate, BigDecimal amount, Employers employer, Suppliers supplier) {
        this.iDImportStock = iDImportStock;
        this.importStockName = importStockName;
        this.contents = contents;
        this.dateAdded = dateAdded;
        this.updatedDate = updatedDate;
        this.amount = amount;
        this.employer = employer;
        this.supplier = supplier;
    }
}
