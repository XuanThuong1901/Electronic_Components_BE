package com.poly.ecommercestore.controller.customer;

import com.poly.ecommercestore.model.request.OrderRequest;
import com.poly.ecommercestore.util.Message;
import com.poly.ecommercestore.service.order.IOrderService;
import com.poly.ecommercestore.util.validator.ValidateInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;


    @GetMapping("")
    public ResponseEntity<?> getAllOrderByCustomer(@RequestHeader("access_token") String tokenHeader){
        return ResponseEntity.ok(orderService.getOrderByCustomer(tokenHeader));
    }

    @GetMapping("/detail/{idOrder}")
    public ResponseEntity<?> getOneOrder(@RequestHeader("access_token") String tokenHeader,@PathVariable(value = "idOrder") int idOrder){
        return ResponseEntity.ok(orderService.getOneOrder(tokenHeader, idOrder));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@RequestHeader("access_token") String tokenHeader, @RequestBody OrderRequest request){

        if(request.getDetailOrders().size() == 0)
            return ResponseEntity.badRequest().body(Message.VALIDATION_DETAIL_ORDER_ERROR001);
        if(request.getAddress() == null || request.getAddress().equals(""))
            return ResponseEntity.badRequest().body(Message.VALIDATION_ADDRESS_ORDER_ERROR001);
        if(request.getTelephone() == null || request.getTelephone().equals(""))
            return ResponseEntity.badRequest().body(Message.VALIDATION_TELEPHONE_ORDER_ERROR001);
        if(request.getShippingUnit() == null)
            return ResponseEntity.badRequest().body(Message.VALIDATION_SHIPPING_UNIT_ORDER_ERROR001);
        if(request.getPayment() == null)
            return ResponseEntity.badRequest().body(Message.VALIDATION_PAYMENT_ORDER_ERROR001);

        if(!ValidateInput.isPhoneNumber(request.getTelephone()))
            return ResponseEntity.badRequest().body(Message.VALIDATION_TELEPHONE_ORDER_ERROR002);

        int check = orderService.addOrder(tokenHeader, request);
        if(check == 0){
            return ResponseEntity.badRequest().body(Message.ORDER_ERROR001);
        }
        if(check == 2){
            return ResponseEntity.badRequest().body(Message.ORDER_ERROR001);
        }

        return ResponseEntity.ok(Message.ORDER_SUCCESS);
    }

    @GetMapping("/canceled/{id}")
    public ResponseEntity<?> canceledOrder(@RequestHeader("access_token") String tokenHeader, @PathVariable(value = "id") int idOrder){

        if(orderService.statusCanceledOrder(tokenHeader, idOrder))
            return ResponseEntity.ok(Message.CANCELED_ORDER_SUCCESS);

        return ResponseEntity.badRequest().body(Message.VALIDATION_CANCELED_ORDER_ERROR001);
    }
}
