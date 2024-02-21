package com.poly.ecommercestore.model.request;

import lombok.Data;

@Data
public class CategoryRequest {

    private String categoryName;
    private Integer level;
    private Integer origin;

    public CategoryRequest(String categoryName, Integer level, Integer origin) {
        this.categoryName = categoryName;
        this.level = level;
        this.origin = (origin != null) ? origin : 0;
    }
}
