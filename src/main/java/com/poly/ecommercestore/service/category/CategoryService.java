package com.poly.ecommercestore.service.category;

import com.poly.ecommercestore.entity.Categories;
import com.poly.ecommercestore.repository.CategoryRepository;
import com.poly.ecommercestore.model.request.CategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Categories addCategory(List<CategoryRequest> request) {
        if(categoryRepository.findByCategoryName(request.get(0).getCategoryName()) != null){
            return null;
        }

        Categories newCategory;
        if(request.get(0).getLevel() == 1){
            newCategory = new Categories(request.get(0).getCategoryName(), request.get(0).getLevel(), 0);
            newCategory = categoryRepository.save(newCategory);
            request.remove(0);
        }
        else {
            return null;
        }


        for (CategoryRequest item : request){
//            if(item.getCategoryName().equals(newCategory.getCategoryName()) && item.getLevel() == newCategory.getLevel()){
//
//            }
            Categories NewDetailCategory = new Categories(item.getCategoryName(), item.getLevel(), newCategory.getIDCategory());
            categoryRepository.save(NewDetailCategory);
        }

        return newCategory;
    }

    @Override
    public List<Categories> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Categories> getCategoryDetail() {
        return categoryRepository.findCategoryDetail();
    }

    @Override
    public Boolean removeCategory(int iDCategory) {
        Categories category = categoryRepository.findById(iDCategory).orElse(null);



//        categoryRepository.deleteById(iDCategory);
        return true;
    }
}
