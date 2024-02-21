package com.poly.ecommercestore.controller.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.poly.ecommercestore.model.response.ReturnOrderResponse;
import com.poly.ecommercestore.service.ReturnOrder.IReturnOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/return_order")
public class ReturnOrderSystemController {

    private final IReturnOrderService iReturnOrderService;

    @GetMapping("")
    public ResponseEntity<ReturnOrderResponse> getAll(@RequestHeader("access_token") String token){
        ReturnOrderResponse response = iReturnOrderService.getAll(token);
        if(response.getMessage().equals("GET_ALL_RETURN_ORDER_SUCCESS")){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnOrderResponse> getById(@RequestHeader("access_token") String token, @PathVariable(name = "id") Integer id){
        ReturnOrderResponse response = iReturnOrderService.getOne(id);
        if(response.getMessage().equals("GET_RETURN_ORDER_BY_ID_SUCCESS")){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping( "/confirmed/{id}/{status}")
    public ResponseEntity<ReturnOrderResponse> confirm(@RequestHeader("access_token") String tokenHeader, @PathVariable(name = "id") Integer id, @PathVariable(name = "status") String status) throws JsonProcessingException {

        System.out.println(tokenHeader);
        ReturnOrderResponse response = iReturnOrderService.confirm(tokenHeader, id, status );
        if(response.getMessage().equals("CONFIRMED_RETURN_ORDER_ERROR001") || response.getMessage().equals("ACCOUNT_NOT_ROLE")){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);

    }
}
