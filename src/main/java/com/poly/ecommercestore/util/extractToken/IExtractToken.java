package com.poly.ecommercestore.util.extractToken;

import com.poly.ecommercestore.entity.Accounts;
import com.poly.ecommercestore.entity.Customers;
import com.poly.ecommercestore.entity.Employers;

public interface IExtractToken {
    String extractRole(String token);
    Accounts extractAccount(String token);

    Customers extractCustomer(String token);
    Employers extractEmployee(String token);
}
