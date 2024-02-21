package com.poly.ecommercestore.model.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductRequest {

    private String productName;
    private Integer category;
    private Integer supplier;
    private Integer tax;
    private Integer quantity;
    private String feature;
    private String contents;
    private String guarantee;
    private List<PriceListRequest> priceList;
    private List<MultipartFile> imageProduct;
    private List<SpecificationRequest> specification;

    public ProductRequest() {
    }

    public ProductRequest(Integer category, Integer supplier, Integer tax, String productName, Integer quantity, String feature, String contents, List<PriceListRequest> priceList, List<MultipartFile> imageProduct, List<SpecificationRequest> specification) {
        this.productName = productName;
        this.category = (category == null) ? category : 0;
        this.supplier = (supplier == null) ? supplier : 0;
        this.tax = (tax == null) ? tax : 0;
        this.quantity = (quantity == null) ? quantity : 0;
        this.feature = (feature != null) ? feature : "N/A";
        this.contents = (contents != null) ? contents : "N/A";
        this.guarantee = (guarantee != null) ? guarantee : "N/A";
        this.priceList = (priceList != null) ? priceList : new ArrayList<>();
        this.imageProduct = (imageProduct != null) ? imageProduct : new ArrayList<>();
        this.specification = (specification != null) ? specification : new ArrayList<>();
    }


}

