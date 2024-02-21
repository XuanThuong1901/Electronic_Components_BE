package com.poly.ecommercestore.model.request;

import lombok.Data;

@Data
public class SpecificationRequest {
    private int idspecification;
    private String specificationName;
    private String parameter;

    public SpecificationRequest() {
    }

    public SpecificationRequest(Integer idspecification, String specificationName, String parameter) {
        this.idspecification = (idspecification != null) ? idspecification : 0;
        this.specificationName = specificationName;
        this.parameter = parameter;
    }
}
