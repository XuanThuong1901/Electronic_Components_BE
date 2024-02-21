package com.poly.ecommercestore.controller.employee;

import com.poly.ecommercestore.util.Message;
import com.poly.ecommercestore.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/order")
@RequiredArgsConstructor
public class OrderSystemController {

    private final IOrderService orderService;

    @GetMapping("")
    public ResponseEntity<?> getAllOrder(@RequestHeader("access_token") String tokenHeader){
        return ResponseEntity.ok(orderService.getAllOrder(tokenHeader));
    }

    @GetMapping("/{idOrder}")
    public ResponseEntity<?> getOneOrder(@RequestHeader("access_token") String tokenHeader, @PathVariable(value = "idOrder") int idOrder){
        return ResponseEntity.ok(orderService.getOneOrder(tokenHeader, idOrder));
    }

    @GetMapping("/confirmed/{id}")
    public ResponseEntity<?> confirmedOrder(@RequestHeader("access_token") String headerToken,@PathVariable(value = "id") int idOrder){
        if(orderService.statusConfirmedOrder(headerToken, idOrder))
            return ResponseEntity.ok(Message.CONFIRMED_ORDER_SUCCESS);

        return ResponseEntity.badRequest().body(Message.VALIDATION_CONFIRMED_ORDER_ERROR001);
    }

    @GetMapping("/delivery/{id}")
    public ResponseEntity<?> deliveryOrder(@RequestHeader("access_token") String tokenHeader, @PathVariable(value = "id") int idOrder){
        if(orderService.statusDeliveryOrder(tokenHeader, idOrder))
            return ResponseEntity.ok(Message.DELIVERY_ORDER_SUCCESS);

        return ResponseEntity.badRequest().body(Message.VALIDATION_DELIVERY_ORDER_ERROR001);
    }

    @GetMapping("/delivered/{id}")
    public ResponseEntity<?> deliveredOrder(@RequestHeader("access_token") String tokenHeader, @PathVariable(value = "id") int idOrder){
        if(orderService.statusDeliveredOrder(tokenHeader, idOrder))
            return ResponseEntity.ok(Message.DELIVERED_ORDER_SUCCESS);

        return ResponseEntity.badRequest().body(Message.VALIDATION_DELIVERED_ORDER_ERROR001);
    }

    @GetMapping("/canceled/{id}")
    public ResponseEntity<?> canceledOrder(@RequestHeader("access_token") String tokenHeader, @PathVariable(value = "id") int idOrder){
        if(orderService.statusCanceledOrder(tokenHeader, idOrder))
            return ResponseEntity.ok(Message.CANCELED_ORDER_SUCCESS);

        return ResponseEntity.badRequest().body(Message.VALIDATION_CANCELED_ORDER_ERROR001);
    }
}
