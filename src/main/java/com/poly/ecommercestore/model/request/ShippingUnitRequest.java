package com.poly.ecommercestore.model.request;

import lombok.Data;

@Data
public class ShippingUnitRequest {

    private String shippingUnitName;

    private String email;

    private String telephone;

    private String address;

//    private BigDecimal shippingCost;

    public ShippingUnitRequest() {
    }

    public ShippingUnitRequest(String shippingUnitName, String email, String telephone, String address) {
        this.shippingUnitName = (shippingUnitName != null) ? shippingUnitName : "N/A";
        this.email = (email != null) ? email : "N/A";
        this.telephone = (telephone != null) ? telephone : "N/A";
        this.address = (address != null) ? address : "N/A";
//        this.shippingCost = shippingCost;
    }

}
