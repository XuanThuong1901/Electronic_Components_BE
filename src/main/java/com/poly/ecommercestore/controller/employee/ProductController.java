package com.poly.ecommercestore.controller.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.ecommercestore.util.Message;
import com.poly.ecommercestore.model.request.ProductRequest;
import com.poly.ecommercestore.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    private final int SIZE = 14;

    @GetMapping("/page={page}")
    public ResponseEntity<?> getProductByPage(@PathVariable(value = "page") int page){
        return ResponseEntity.ok(productService.getProductByPage(page, SIZE));
    }
    @GetMapping("")
    public ResponseEntity<?> getProductByPage(){
        return ResponseEntity.ok(productService.getProduct());
    }

    @GetMapping("/detailCategory={id}/{page}")
    public ResponseEntity<?> getProductByDetailCategoryByPage(@PathVariable(value = "id") int id, @PathVariable(value = "page") int page){
        System.out.printf("id:" +id + "page: " + page);

        return ResponseEntity.ok(productService.getProductByCategoryByPage(id, page, 6));
    }

    @GetMapping("/product={id}")
    public ResponseEntity<?> getOneProduct(@PathVariable(value = "id") int id) throws IOException {
        return ResponseEntity.ok(productService.getProductById(id));
    }

//    @PostMapping("/add")
//    public ResponseEntity<?> addProduct(@RequestHeader("access_token") String tokenHeader, @RequestParam("productDTO") String productDTO, @RequestParam("imageProduct") List<MultipartFile> imageProduct) throws JsonProcessingException {
//        ProductRequest product = new ObjectMapper().readValue(productDTO, ProductRequest.class);
//        System.out.println(product);
//
//        if(product.getProductName() == null){
//            return ResponseEntity.badRequest().body("Product not name");
//        }
//        if(product.getQuantity() == null){
//            return ResponseEntity.badRequest().body("Product not quantity");
//        }
//        if(product.getPriceList().size() == 0){
//            return ResponseEntity.badRequest().body("Product not price");
//        }
//        if(imageProduct.size() == 0){
//            return ResponseEntity.badRequest().body("Product not image");
//        }
//        if(product.getSpecification().size() == 0){
//            return ResponseEntity.badRequest().body("Product not specification");
//        }
//
//        productService.addProduct(product, imageProduct, tokenHeader);
//        return ResponseEntity.ok("Product created");
//    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestHeader("access_token") String tokenHeader, @RequestParam("productDTO") String request, @RequestParam("imageProduct") List<MultipartFile> imageProduct) throws JsonProcessingException {
        ProductRequest product = new ObjectMapper().readValue(request, ProductRequest.class);

        if(product.getProductName().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_NAME_PRODUCT_ERROR001);
        }
        if(product.getCategory() == 0){
            return ResponseEntity.badRequest().body(Message.VALIDATION_CATEGORY_PRODUCT_ERROR001);
        }
        if(product.getSupplier() == 0){
            return ResponseEntity.badRequest().body(Message.VALIDATION_SUPPLIER_PRODUCT_ERROR001);
        }
        if(product.getTax() == 0){
            return ResponseEntity.badRequest().body(Message.VALIDATION_TAX_PRODUCT_ERROR001);
        }
        if(product.getContents().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_CONTENTS_PRODUCT_ERROR001);
        }
        if(product.getFeature().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_FEATURE_PRODUCT_ERROR001);
        }
        System.out.print(product.getPriceList());
        if(product.getPriceList().size() == 0){
            return ResponseEntity.badRequest().body(Message.VALIDATION_PRICE_PRODUCT_ERROR001);
        }
        if(imageProduct.size() == 0){
            return ResponseEntity.badRequest().body(Message.VALIDATION_IMAGE_PRODUCT_ERROR001);
        }
        if(product.getSpecification().size() == 0){
            return ResponseEntity.badRequest().body(Message.VALIDATION_SPECIFICATION_PRODUCT_ERROR001);
        }

        int isCheck = productService.addProduct(product, imageProduct, tokenHeader);
        if(isCheck == 0)
            return ResponseEntity.badRequest().body(Message.ADD_PRODUCT_ERROR001);
        if(isCheck == 2)
            return ResponseEntity.badRequest().body(Message.VALIDATION_NAME_PRODUCT_ERROR002);

        return ResponseEntity.ok(Message.ADD_PRODUCT_SUCCESS);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@RequestHeader("access_token") String tokenHeader , @PathVariable(value = "id") int id, @RequestParam("productDTO") String request, @RequestParam("imageProduct") List<MultipartFile> imageProduct) throws JsonProcessingException {

        ProductRequest product = new ObjectMapper().readValue(request, ProductRequest.class);

        if(product.getProductName().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_NAME_PRODUCT_ERROR001);
        }
        if(product.getCategory() == 0){
            return ResponseEntity.badRequest().body(Message.VALIDATION_CATEGORY_PRODUCT_ERROR001);
        }
        if(product.getSupplier() == 0){
            return ResponseEntity.badRequest().body(Message.VALIDATION_SUPPLIER_PRODUCT_ERROR001);
        }
        if(product.getTax() == 0){
            return ResponseEntity.badRequest().body(Message.VALIDATION_TAX_PRODUCT_ERROR001);
        }
        if(product.getContents().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_CONTENTS_PRODUCT_ERROR001);
        }
        if(product.getFeature().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_FEATURE_PRODUCT_ERROR001);
        }

        if(product.getPriceList().size() == 0){
            return ResponseEntity.badRequest().body(Message.VALIDATION_PRICE_PRODUCT_ERROR001);
        }
        if(imageProduct.size() == 0){
            return ResponseEntity.badRequest().body(Message.VALIDATION_IMAGE_PRODUCT_ERROR001);
        }
        if(product.getSpecification().size() == 0){
            return ResponseEntity.badRequest().body(Message.VALIDATION_SPECIFICATION_PRODUCT_ERROR001);
        }

        if(productService.updateProduct(tokenHeader ,id, product, imageProduct))
            return ResponseEntity.ok(Message.UPDATE_PRODUCT_SUCCESS);

        return ResponseEntity.badRequest().body(Message.UPDATE_PRODUCT_ERROR001);
    }

}
