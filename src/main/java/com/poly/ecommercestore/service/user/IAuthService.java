package com.poly.ecommercestore.service.user;

import com.poly.ecommercestore.entity.Accounts;
import com.poly.ecommercestore.model.request.AccountRequest;
import com.poly.ecommercestore.model.request.UserRequest;
import com.poly.ecommercestore.model.response.AuthResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IAuthService {

    AuthResponse login(AccountRequest request);

    boolean registerCustomer(UserRequest user);

    boolean registerEmployee(UserRequest user, MultipartFile avatar);

    Boolean resetPassword(String email);
}
