package com.poly.ecommercestore.service.user;

import com.poly.ecommercestore.model.request.AccountRequest;
import com.poly.ecommercestore.model.request.UserRequest;

public interface IUserService {

    public Object getUser(String tokenHeader);

    public Object updateUser(String tokenHeader, String idAccount, UserRequest user);

    public Boolean updatePassword(String tokenHeader, AccountRequest account);

//    public Boolean setWorkingUser(String tokenHeader, String idAccount);

    public Boolean setStatusUser(String tokenHeader, String idAccount, int idStatus);
}
