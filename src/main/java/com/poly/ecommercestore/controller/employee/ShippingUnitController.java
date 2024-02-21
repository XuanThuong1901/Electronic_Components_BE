package com.poly.ecommercestore.controller.employee;

import com.poly.ecommercestore.model.request.ShippingUnitRequest;
import com.poly.ecommercestore.util.Message;
import com.poly.ecommercestore.service.shippingunit.IShippingUnitService;
import com.poly.ecommercestore.util.validator.ValidateInput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipping_unit")
@RequiredArgsConstructor
public class ShippingUnitController {

    private final IShippingUnitService shippingUnitService;

    @GetMapping("")
    public ResponseEntity<?> getAllShippingUnit(){
        return ResponseEntity.ok(shippingUnitService.getAllShippingUnit());
    }

    @PostMapping("/add")
    private ResponseEntity<?> addShippingUnit(@RequestHeader("access_token")String tokenHeader, @RequestBody ShippingUnitRequest request){
        if(request.getShippingUnitName().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_NAME_SHIPPING_UNIT_ERROR001);
        }
        if(request.getEmail().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_EMAIL_SHIPPING_UNIT_ERROR001);
        }
        if(request.getTelephone().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_TELEPHONE_SHIPPING_UNIT_ERROR001);
        }
        if(request.getAddress().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_ADDRESS_SHIPPING_UNIT_ERROR001);
        }
        if(!ValidateInput.isPhoneNumber(request.getTelephone())){
            return ResponseEntity.badRequest().body(Message.VALIDATION_TELEPHONE_SHIPPING_UNIT_ERROR002);
        }

        if(shippingUnitService.addShippingUnit(tokenHeader, request) == false)
            return ResponseEntity.badRequest().body(Message.ADD_SHIPPING_UNIT_ERROR001);

        return ResponseEntity.ok(Message.ADD_SHIPPING_UNIT_SUCCESS);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateShippingUnit(@RequestHeader("access_token") String tokenHeader, @RequestBody ShippingUnitRequest request, @PathVariable(value = "id") int id){
        if(request.getShippingUnitName() == null || request.getShippingUnitName().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_NAME_SHIPPING_UNIT_ERROR001);
        }
        if(request.getEmail() == null || request.getEmail().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_EMAIL_SHIPPING_UNIT_ERROR001);
        }
        if(request.getTelephone() == null || request.getTelephone().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_TELEPHONE_SHIPPING_UNIT_ERROR001);
        }
        if(request.getAddress() == null || request.getAddress().isEmpty()){
            return ResponseEntity.badRequest().body(Message.VALIDATION_ADDRESS_SHIPPING_UNIT_ERROR001);
        }
        if(shippingUnitService.updateShippingUnit(tokenHeader, request, id) == null){
            return ResponseEntity.badRequest().body(Message.UPDATE_SHIPPING_UNIT_ERROR001);
        }

        return ResponseEntity.ok(Message.UPDATE_SHIPPING_UNIT_SUCCESS);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteShippingUnit(@PathVariable(value = "id") int id){
        if(shippingUnitService.deleteShippingUnit(id) == false)
            return ResponseEntity.badRequest().body(Message.DELETE_SHIPPING_UNIT_ERROR001);

        return ResponseEntity.ok(Message.DELETE_SHIPPING_UNIT_SUCCESS);
    }

}
