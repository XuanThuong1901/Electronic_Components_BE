package com.poly.ecommercestore.service.importstock;

import com.poly.ecommercestore.entity.ImportStocks;
import com.poly.ecommercestore.model.request.ImportStockRequest;

import java.util.List;

public interface IImportStockService {

    boolean addImportStock(ImportStockRequest request, String tokenHeader);

    List<ImportStocks> getAllImportStock();

    ImportStocks updateImportStock(ImportStockRequest request);

    ImportStocks getOneImportStock(int iDImportStock);

    Boolean completeImport(String tokenHeader, int idImport);
}
