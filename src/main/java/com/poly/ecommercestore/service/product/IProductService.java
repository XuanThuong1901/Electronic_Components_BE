package com.poly.ecommercestore.service.product;

import com.poly.ecommercestore.entity.Products;
import com.poly.ecommercestore.model.request.ProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IProductService {

    int addProduct(ProductRequest request, List<MultipartFile> imageProduct, String iDEmployer);

    boolean updateProduct(String tokenHeader , int iDProduct, ProductRequest request, List<MultipartFile> imageProduct);

    boolean removeProduct(int iDProduct);

    Products getProductById(int iDProduct) throws IOException;

    List<Products> getProduct();

    List<Products> getProductByPage(int page, int size);

    List<Products> getProductByCategoryByPage(int iDDetailCategory, int page, int size);

}
