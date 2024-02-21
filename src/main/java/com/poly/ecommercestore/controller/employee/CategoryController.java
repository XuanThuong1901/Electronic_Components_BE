package com.poly.ecommercestore.controller.employee;

import com.poly.ecommercestore.model.request.CategoryRequest;
import com.poly.ecommercestore.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<?> getAllCategory(){
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getAllCategoryDetail(){
        return ResponseEntity.ok(categoryService.getCategoryDetail());
    }
    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody List<CategoryRequest> request){
        if(request.size() <=1)
            return ResponseEntity.badRequest().body("Detail Category not null");

        return ResponseEntity.ok(categoryService.addCategory(request));
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<?> removeCategory(@PathVariable(value = "id") int iDCategory){

        Boolean check = categoryService.removeCategory(iDCategory);
        if(!check)
            return ResponseEntity.badRequest().body("ECommerceMessage.SYSTEM_ERROR");

        return ResponseEntity.ok("ECommerceMessage.CATEGORY_DELETED");
    }
}
