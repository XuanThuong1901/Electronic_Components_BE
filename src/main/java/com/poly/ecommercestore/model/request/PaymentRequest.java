package com.poly.ecommercestore.model.request;

import lombok.Data;

@Data
public class PaymentRequest {

    private String paymentName;
    private String contents;

    public PaymentRequest() {
    }

    public PaymentRequest(String paymentName, String contents) {
        this.paymentName = paymentName;
        this.contents = (contents != null) ? contents : "N/A";
    }
}
