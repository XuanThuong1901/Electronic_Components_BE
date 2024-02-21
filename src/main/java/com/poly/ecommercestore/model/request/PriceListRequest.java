package com.poly.ecommercestore.model.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PriceListRequest {

    private  int idprice;
    private String type;
    private Boolean status;
    private Date applicableDate;
    private Date updateDate;
    private BigDecimal price;

    public PriceListRequest() {
    }

    public PriceListRequest(Integer idprice, String type, Boolean status, Date applicableDate, Date updateDate, BigDecimal price) {
        this.idprice = (idprice != null) ? idprice : 0;
        this.type = type;
        this.status = (status != null) ? status : null;
        this.applicableDate = (applicableDate != null) ? applicableDate : null;
        this.updateDate = (updateDate != null) ? updateDate : null;
        this.price = price;
    }
}
