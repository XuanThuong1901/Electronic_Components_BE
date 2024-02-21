package com.poly.ecommercestore.controller.employee;

import com.poly.ecommercestore.model.request.PaymentRequest;
import com.poly.ecommercestore.util.Message;
import com.poly.ecommercestore.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("")
    public ResponseEntity<?> getAllPayment(){
        return ResponseEntity.ok(paymentService.getAllPayment());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPayment(@RequestBody PaymentRequest request){

        if(request.getPaymentName() == null || request.getPaymentName().equals("")){
            return ResponseEntity.badRequest().body(Message.VALIDATION_NAME_PAYMENT_ERROR001);
        }

        if(paymentService.addPayment(request) != null)
            return ResponseEntity.ok(Message.ADD_PAYMENT_SUCCESS);

        return ResponseEntity.badRequest().body(Message.ADD_PAYMENT_ORDER_ERROR001);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updatePayment(@RequestBody PaymentRequest request, @PathVariable(value = "id") int idPayment){

        if(request.getPaymentName() == null || request.getPaymentName().equals("")){
            return ResponseEntity.badRequest().body(Message.VALIDATION_NAME_PAYMENT_ERROR001);
        }

        if(paymentService.updatePayment(request, idPayment) == true)
            return ResponseEntity.ok(Message.UPDATE_PAYMENT_SUCCESS);

        return ResponseEntity.badRequest().body(Message.UPDATE_PAYMENT_ORDER_ERROR001);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable(value = "id") int idPayment){

        if(paymentService.deletePayment(idPayment) == true)
            return ResponseEntity.ok(Message.DELETE_PAYMENT_SUCCESS);

        return ResponseEntity.badRequest().body(Message.DELETE_PAYMENT_ORDER_ERROR001);
    }
}
