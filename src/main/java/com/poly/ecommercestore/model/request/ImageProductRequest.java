package com.poly.ecommercestore.model.request;

import lombok.Data;

@Data
public class ImageProductRequest {

    private String url;

    public ImageProductRequest() {
    }

    public ImageProductRequest(String url) {
        this.url = url;
    }
}
