package com.poly.ecommercestore.model.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderRequest {

    private String address;
    private String telephone;
    private String note;
    private Integer shippingUnit;
    private Integer payment;
    private BigDecimal shippingFee;
    private BigDecimal taxAmount;
    private BigDecimal discountAmount;
    private BigDecimal amount;
    private List<DetailOrderRequest> detailOrders;

    public OrderRequest() {
    }


    public OrderRequest(String address, String telephone, String note, Integer shippingUnit, Integer payment, BigDecimal shippingFee, BigDecimal taxAmount, BigDecimal discountAmount, BigDecimal amount, List<DetailOrderRequest> detailOrders) {
        this.address = address;
        this.telephone = telephone;
        this.note = (note != null) ? note : "N/A";
        this.shippingUnit = shippingUnit;
        this.payment = payment;
        this.shippingFee = shippingFee;
        this.taxAmount = taxAmount;
        this.discountAmount = discountAmount;
        this.amount = amount;
        this.detailOrders = (detailOrders != null) ? new ArrayList<>() : null;
    }
}
