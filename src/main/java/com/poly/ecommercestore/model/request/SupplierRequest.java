package com.poly.ecommercestore.model.request;

import lombok.Data;

@Data
public class SupplierRequest {

    private String supplierName;

    private String email;

    private String telephone;

    private String address;

    public SupplierRequest() {
    }

    public SupplierRequest(String supplierName, String email, String telephone, String address) {
        this.supplierName = (supplierName != "") ? supplierName : "N/A";
        this.email = (email != "") ? email : "N/A";
        this.telephone = (telephone != "") ? telephone : "N/A";
        this.address = (address != "") ? address : "N/A";
    }
}
