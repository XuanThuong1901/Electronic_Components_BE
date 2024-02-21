package com.poly.ecommercestore.controller.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.ecommercestore.model.request.ReturnOrderRequest;
import com.poly.ecommercestore.model.response.ReturnOrderResponse;
import com.poly.ecommercestore.service.ReturnOrder.IReturnOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/return_order")
public class ReturnOrderController {

    private final IReturnOrderService iReturnOrderService;

    @GetMapping("")
    public ResponseEntity<ReturnOrderResponse> getByCustomer(@RequestHeader("access_token") String token){
        ReturnOrderResponse response = iReturnOrderService.getByCustomer(token);
        if(response.getMessage().equals("GET_RETURN_ORDER_BY_CUSTOMER_SUCCESS")){
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

    @PostMapping(path = "/add")
    public ResponseEntity<ReturnOrderResponse> add(@RequestHeader("access_token") String token, @RequestParam("returnOrderRequest") String stringRequest, @RequestParam("imgProductReturn")List<MultipartFile> imaProductReturn) throws JsonProcessingException {

        ReturnOrderRequest request = new ObjectMapper().readValue(stringRequest, ReturnOrderRequest.class);

        System.out.println(request);
        if(request.getAddress().isEmpty()){
            return ResponseEntity.badRequest().body(ReturnOrderResponse.builder()
                    .message("VALIDATE_ADDRESS_RETURN_ORDER_ERROR001")
                    .build());
        }
        if(request.getPhoneNumber().isEmpty()){
            return ResponseEntity.badRequest().body(ReturnOrderResponse.builder()
                    .message("VALIDATE_PHONE_NUMBER_RETURN_ORDER_ERROR001")
                    .build());
        }
        if(request.getReason().isEmpty()){
            return ResponseEntity.badRequest().body(ReturnOrderResponse.builder()
                    .message("VALIDATE_REASON_RETURN_ORDER_ERROR001")
                    .build());
        }
        if(request.getFormality().isEmpty()){
            return ResponseEntity.badRequest().body(ReturnOrderResponse.builder()
                    .message("VALIDATE_FORMALITY_RETURN_ORDER_ERROR001")
                    .build());
        }
        if(imaProductReturn.size() <= 0){
            return ResponseEntity.badRequest().body(ReturnOrderResponse.builder()
                    .message("VALIDATE_IMAGE_PRODUCT_RETURN_ORDER_ERROR001")
                    .build());
        }

        ReturnOrderResponse response = iReturnOrderService.add(token, request, imaProductReturn );
        if(response.getMessage().equals("CREATED_RETURN_ORDER_SUCCESS")){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body(response);
    }
}
