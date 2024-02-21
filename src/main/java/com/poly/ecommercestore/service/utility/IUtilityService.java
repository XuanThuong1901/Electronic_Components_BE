package com.poly.ecommercestore.service.utility;

import com.poly.ecommercestore.entity.Customers;
import com.poly.ecommercestore.entity.Employers;

import java.util.List;

public interface IUtilityService {

    List<Customers> getAllCustomer();
    List<Employers> getAllEmployee();

    Employers getEmployee(String tokenHeader, String id);
}
