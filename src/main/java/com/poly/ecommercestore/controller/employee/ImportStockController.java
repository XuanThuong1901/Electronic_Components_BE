package com.poly.ecommercestore.controller.employee;

import com.poly.ecommercestore.model.request.ImportStockRequest;
import com.poly.ecommercestore.util.Message;
import com.poly.ecommercestore.service.importstock.IImportStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/import")
@RequiredArgsConstructor
public class ImportStockController {

    private final IImportStockService importStockService;

    @GetMapping("")
    public ResponseEntity<?> getAllImport(){
        return ResponseEntity.ok(importStockService.getAllImportStock());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getImport(@PathVariable(value = "id") int id){
        return ResponseEntity.ok(importStockService.getOneImportStock(id));
    }


    @PostMapping("/add")
    public ResponseEntity<?> addImport(@RequestHeader("access_token") String tokenHeader, @RequestBody ImportStockRequest request){

        if(request.getImportStockName() == null){
            return ResponseEntity.badRequest().body(Message.VALIDATION_NAME_IMPORT_ERROR001);
        }

        if(request.getSupplier() == null){
            return ResponseEntity.badRequest().body(Message.VALIDATION_SUPPLIER_IMPORT_ERROR001);
        }
        if(request.getDetailImportStocks() == null){
            return ResponseEntity.badRequest().body(Message.VALIDATION_DETAIL_IMPORT_ERROR001);
        }

        if(!importStockService.addImportStock(request, tokenHeader)){
            return ResponseEntity.badRequest().body(Message.VALIDATION_IMPORT_ERROR001);
        }

        return ResponseEntity.ok(Message.IMPORT_SUCCESS);
    }

    @GetMapping("/complete/{id}")
    public ResponseEntity<?> completeImport(@RequestHeader("access_token") String tokenHeader, @PathVariable(value = "id") int id){
        if(importStockService.completeImport(tokenHeader, id))
            return ResponseEntity.ok(Message.COMPLETE_IMPORT_SUCCESS);
        return ResponseEntity.badRequest().body(Message.VALIDATION_COMPLETE_IMPORT_ERROR001);
    }
}
