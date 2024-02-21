package com.poly.ecommercestore.service.category;

import com.poly.ecommercestore.entity.Categories;
import com.poly.ecommercestore.model.request.CategoryRequest;

import java.util.List;

public interface ICategoryService {

    Categories addCategory(List<CategoryRequest> requests);

    List<Categories> getAll();

    List<Categories> getCategoryDetail();

    Boolean removeCategory(int iDCategory);
}
