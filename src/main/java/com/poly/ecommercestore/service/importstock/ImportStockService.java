package com.poly.ecommercestore.service.importstock;

import com.poly.ecommercestore.entity.*;
import com.poly.ecommercestore.entity.embeddable.DetailImportStockId;
import com.poly.ecommercestore.repository.*;
import com.poly.ecommercestore.model.request.DetailImportRequest;
import com.poly.ecommercestore.model.request.ImportStockRequest;
import com.poly.ecommercestore.util.extractToken.IExtractToken;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportStockService implements IImportStockService{

    private final IExtractToken iExtractToken;
    private final ImportStockRepository importStockRepository;
    private final DetailImportStockRepository detailImportStockRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final StatusRepository statusRepository;

    private final int PROCESSING = 8;
    private final int COMPLETED = 9;

    @Override
    @Transactional
    public boolean addImportStock(ImportStockRequest request, String tokenHeader) {

        try{
            if(request.getDetailImportStocks() == null)
                return false;

            Employers employer = iExtractToken.extractEmployee(tokenHeader);
            if(employer == null)
                return false;

            Suppliers supplier = supplierRepository.getReferenceById(request.getSupplier());

            Status status = statusRepository.getReferenceById(PROCESSING);

            ImportStocks newImportStock = new ImportStocks();
            newImportStock.setEmployer(employer);
            newImportStock.setSupplier(supplier);
            newImportStock.setStatus(status);
            newImportStock.setImportStockName(request.getImportStockName());
            newImportStock.setContents(request.getContents());
            newImportStock.setDateAdded(new Date());
            newImportStock.setUpdatedDate(new Date());

            newImportStock = importStockRepository.save(newImportStock);

            List<DetailImportStocks> newDetailImportStocks = new ArrayList<>();
            BigDecimal amount = new BigDecimal(0);
            for (DetailImportRequest detailImport : request.getDetailImportStocks()){

                Products product = productRepository.findByProductName(detailImport.getProduct());

                DetailImportStockId detailImportStockId = new DetailImportStockId(newImportStock.getIDImportStock(), product.getIDProduct());

                DetailImportStocks detailImportStockTemp = new DetailImportStocks(detailImportStockId, detailImport.getQuantity(), detailImport.getPrice(), newImportStock, product);
                newDetailImportStocks.add(detailImportStockTemp);
                amount = amount.add(detailImport.getPrice().multiply(BigDecimal.valueOf(detailImport.getQuantity())));
                product.setQuantity(product.getQuantity() + detailImport.getQuantity());
                productRepository.save(product);
            }
            newImportStock.setAmount(amount);
            importStockRepository.save(newImportStock);
            detailImportStockRepository.saveAll(newDetailImportStocks);
            return true;
        }catch (Exception e){
            return false;
        }


    }

    @Override
    public List<ImportStocks> getAllImportStock() {
        return importStockRepository.findAll();
    }

    @Override
    public ImportStocks updateImportStock(ImportStockRequest request) {
        return null;
    }

    @Override
    public ImportStocks getOneImportStock(int iDImportStock) {
        return importStockRepository.findById(iDImportStock).get();
    }

    @Override
    public Boolean completeImport(String tokenHeader, int idImport) {
        Employers employer = iExtractToken.extractEmployee(tokenHeader);
        ImportStocks importStock = importStockRepository.findById(idImport).get();
        if(employer == null || importStock == null)
            return false;
        Status status = statusRepository.findById(COMPLETED).get();
        importStock.setStatus(status);
        importStockRepository.save(importStock);
        return true;
    }

}
