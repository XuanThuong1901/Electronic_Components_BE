package com.poly.ecommercestore.model.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class OderEmployerRequest {

    private BigDecimal price;
    private String address;
    private String telephone;
    private String note;
    private String customer;
    private String employer;
    private Integer shippingUnit;
    private Integer payment;
    private Integer statusOrder;
    private List<DetailOrderRequest> detailOrders;

    public OderEmployerRequest() {
    }

    public OderEmployerRequest(BigDecimal price, String address, String telephone, String note, String customer, String employer, Integer shippingUnit, Integer payment, Integer statusOrder, List<DetailOrderRequest> detailOrders) {
        this.price = (price != null) ? price : null;
        this.address = (address != null) ? address : "N/A";
        this.telephone = (telephone != null) ? telephone : "N/A";
        this.note = (note != null) ? note : "N/A";
        this.customer = (customer != null) ? customer : "N/A";
        this.employer = (employer != null) ? employer : "N/A";
        this.shippingUnit = (shippingUnit != null) ? shippingUnit : 0;
        this.payment = (payment != null) ? payment : 0;
        this.statusOrder = (statusOrder != null) ? statusOrder : 0;
        this.detailOrders = (detailOrders != null) ? new ArrayList<>() : null;
    }
}
