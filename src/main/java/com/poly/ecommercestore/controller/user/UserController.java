package com.poly.ecommercestore.controller.user;

import com.poly.ecommercestore.util.Message;
import com.poly.ecommercestore.model.request.AccountRequest;
import com.poly.ecommercestore.model.request.UserRequest;
import com.poly.ecommercestore.service.user.UserService;
import com.poly.ecommercestore.util.validator.ValidateInput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("")
    public ResponseEntity<?> getUser(@RequestHeader("access_token") String tokenHeader){
        return ResponseEntity.ok(userService.getUser(tokenHeader));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@RequestHeader("access_token") String tokenHeader, @PathVariable(value = "id") String idAccount, @RequestBody UserRequest userDTO){

        if(userService.updateUser(tokenHeader, idAccount, userDTO) != null){
            return ResponseEntity.ok(Message.UPDATE_USER_SUCCESS);
        }
        return ResponseEntity.badRequest().body(Message.UPDATE_USER_ERROR001);
    }

    @PostMapping("/updatepassword")
    public ResponseEntity<String> updatePass(@RequestHeader("access_token") String tokenHeader,@RequestBody AccountRequest accountRequest){

        if(accountRequest.getPassword().isEmpty())
        {
            return ResponseEntity.badRequest().body(Message.VALIDATION_PASSWORD_ERROR001);
        }
        if(!ValidateInput.isPasswordValid(accountRequest.getPassword()))
            return ResponseEntity.badRequest().body(Message.VALIDATION_PASSWORD_ERROR002);

        if(userService.updatePassword(tokenHeader, accountRequest))
            return ResponseEntity.ok(Message.UPDATE_PASSWORD_SUCCESS);
        return ResponseEntity.badRequest().body(Message.UPDATE_PASSWORD_ERROR001);
    }

}
