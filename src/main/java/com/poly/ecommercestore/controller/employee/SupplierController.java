package com.poly.ecommercestore.controller.employee;

import com.poly.ecommercestore.model.request.SupplierRequest;
import com.poly.ecommercestore.util.Message;
import com.poly.ecommercestore.service.supplier.SupplierService;
import com.poly.ecommercestore.util.validator.ValidateInput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/supplier")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping("")
    public ResponseEntity<?> getAllSupplier(){
        return ResponseEntity.ok(supplierService.getAllSupplier());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addSupplier(@RequestHeader("access_token") String tokenHeader, @RequestBody SupplierRequest request){
        if(request.getSupplierName().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_NAME_SUPPLIER_ERROR001);
        }
        if(request.getEmail().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_EMAIL_SUPPLIER_ERROR001);
        }
        if(request.getTelephone().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_TELEPHONE_SUPPLIER_ERROR001);
        }
        if(request.getAddress().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_ADDRESS_SUPPLIER_ERROR001);
        }
        if(!ValidateInput.isPhoneNumber(request.getTelephone())){
            return ResponseEntity.badRequest().body(Message.VALIDATION_TELEPHONE_SUPPLIER_ERROR002);
        }


        if(supplierService.addSupplier(tokenHeader, request))
            return ResponseEntity.ok(Message.ADD_SUPPLIER_SUCCESS);
        return ResponseEntity.badRequest().body(Message.ADD_SUPPLIER_ERROR001);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateSupplier(@RequestHeader("access_token") String tokenHeader, @RequestBody SupplierRequest request, @PathVariable(value = "id") int id){

        if(request.getSupplierName().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_NAME_SUPPLIER_ERROR001);
        }
        if(request.getEmail().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_EMAIL_SUPPLIER_ERROR001);
        }
        if(request.getTelephone().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_TELEPHONE_SUPPLIER_ERROR001);
        }
        if(request.getAddress().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_ADDRESS_SUPPLIER_ERROR001);
        }
        if(!ValidateInput.isPhoneNumber(request.getTelephone())){
            return ResponseEntity.badRequest().body(Message.VALIDATION_TELEPHONE_SUPPLIER_ERROR002);
        }

        if(supplierService.updateSupplier(tokenHeader,request, id)){
            return ResponseEntity.ok(Message.UPDATE_SUPPLIER_SUCCESS);
        }
        return ResponseEntity.badRequest().body(Message.UPDATE_SUPPLIER_ERROR001);


    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable(value = "id") int id){
        if(supplierService.deleteSupplier(id) == false)
            return ResponseEntity.badRequest().body(Message.DELETE_SUPPLIER_ERROR001);

        return ResponseEntity.ok(Message.DELETE_SUPPLIER_SUCCESS);
    }
}
