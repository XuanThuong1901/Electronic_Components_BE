package com.poly.ecommercestore.controller.employee;

import com.poly.ecommercestore.util.Message;
import com.poly.ecommercestore.service.Tax.TaxService;
import com.poly.ecommercestore.service.status.StatusService;
import com.poly.ecommercestore.service.user.UserService;
import com.poly.ecommercestore.service.utility.UtilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class UtilityController {

    private final StatusService statusServices;
    private final TaxService taxService;
    private final UtilityService utilityService;
    private final UserService userService;

    @GetMapping("/status/{type}")
    public ResponseEntity<?> getStatus(@PathVariable(value = "type") String type){
        return ResponseEntity.ok(statusServices.getStatus(type));
    }

    @GetMapping("/tax")
    public ResponseEntity<?> getTax(){
        return ResponseEntity.ok(taxService.getAllTax());
    }

    @GetMapping("/admin/customer")
    public ResponseEntity<?> getAllCustomer(){
        return ResponseEntity.ok(utilityService.getAllCustomer());
    }

    @GetMapping("/admin/employee")
    public ResponseEntity<?> getAllEmployee(){
        return ResponseEntity.ok(utilityService.getAllEmployee());
    }

    @GetMapping("/admin/employee/{id}")
    public ResponseEntity<?> getEmployee(@RequestHeader("access_token") String tokenHeader,@PathVariable(value = "id") String id ){
        return ResponseEntity.ok(utilityService.getEmployee(tokenHeader, id));
    }

    @GetMapping("/admin/user/status/{id}/{status}")
    public ResponseEntity<?> setWorkingUser(@RequestHeader("access_token") String tokenHeader, @PathVariable(value = "id") String idUser, @PathVariable(value = "status") int status){

        boolean isCheck = userService.setStatusUser(tokenHeader, idUser, status);
        if(!isCheck)
            return ResponseEntity.badRequest().body(Message.UPDATE_STATUS_USER_ERROR001);
        return ResponseEntity.ok(Message.UPDATE_STATUS_USER_SUCCESS);
    }
}
