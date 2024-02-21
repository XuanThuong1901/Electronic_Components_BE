package com.poly.ecommercestore.model.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryListRequest {

    private List<CategoryRequest> categoryDTOList;

    public CategoryListRequest() {
    }

    public CategoryListRequest(List<CategoryRequest> categoryDTOList) {
        this.categoryDTOList = (categoryDTOList != null) ? new ArrayList<>() : null;
    }
}
