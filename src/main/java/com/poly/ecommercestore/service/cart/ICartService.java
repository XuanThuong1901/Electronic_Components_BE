package com.poly.ecommercestore.service.cart;

import com.poly.ecommercestore.entity.Carts;
import com.poly.ecommercestore.model.request.CartRequest;

import java.util.List;

public interface ICartService {

    List<Carts> getCartByCustomer(String iDCustomer);

    Carts addCart(String iDCustomer, int iDProduct, CartRequest request);

    Boolean deleteCart(String iDCustomer, int iDProduct);

    Boolean updateCart(String iDCustomer, int iDProduct, CartRequest request);

}
