package com.poly.ecommercestore.model.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ImportStockRequest {
    private String importStockName;
    private String contents;
//    private String employer;
    private Integer supplier;
    private List<DetailImportRequest> detailImportStocks;

    public ImportStockRequest() {
    }

    public ImportStockRequest(String importStockName, String contents, Integer supplier, List<DetailImportRequest> detailImportStocks) {
        this.importStockName = importStockName;
        this.contents = (contents != null) ? contents : "N/A";
//        this.employer = employer;
        this.supplier = supplier;
        this.detailImportStocks = (detailImportStocks == null) ? null : new ArrayList<>();
    }
}
