package com.poly.ecommercestore.service.cart;

import com.poly.ecommercestore.configuration.jwt.JwtService;
import com.poly.ecommercestore.entity.Accounts;
import com.poly.ecommercestore.entity.Carts;
import com.poly.ecommercestore.entity.Customers;
import com.poly.ecommercestore.entity.Products;
import com.poly.ecommercestore.entity.embeddable.CartId;
import com.poly.ecommercestore.repository.CartRepository;
import com.poly.ecommercestore.repository.CustomerRepository;
import com.poly.ecommercestore.repository.ProductRepository;
import com.poly.ecommercestore.model.request.CartRequest;
import com.poly.ecommercestore.util.extractToken.IExtractToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{

    private final IExtractToken iExtractToken;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    private final Boolean SELECT_STATE = false;

    @Override
    public List<Carts> getCartByCustomer(String tokenHeader) {

        Customers customer = getCustomer(tokenHeader);

        if(customer == null)
            return null;
        return cartRepository.findByCustomer(customer.getIDCustomer());
    }

    @Override
    public Carts addCart(String tokenHeader, int iDProduct, CartRequest request) {
        Customers customer = getCustomer(tokenHeader);
        if(customer == null)
            return null;

        Products product = productRepository.getReferenceById(iDProduct);
        if(product == null)
            return null;

        Date dateUpdate = new Date();

        Carts newCart = cartRepository.findByCustomerAndProduct(customer.getIDCustomer(), product.getIDProduct());
        if(newCart == null){
            CartId cartId = new CartId(customer.getIDCustomer(), product.getIDProduct());
            newCart = new Carts(cartId, SELECT_STATE, dateUpdate, request.getQuantity(), customer, product);
        }
        else {
            newCart.setQuantity(newCart.getQuantity() + request.getQuantity());
            newCart.setUpdatedDate(dateUpdate);
        }

        cartRepository.save(newCart);

        return newCart;
    }

    @Override
    public Boolean deleteCart(String tokenHeader, int iDProduct) {
        Customers customer = getCustomer(tokenHeader);
        Carts cart = cartRepository.findByCustomerAndProduct(customer.getIDCustomer(), iDProduct);
        if(cart == null)
            return false;

        cartRepository.delete(cart);
        return true;
    }

    @Override
    public Boolean updateCart(String tokenHeader, int iDProduct, CartRequest request) {
        Customers customer = getCustomer(tokenHeader);
        Carts cart = cartRepository.findByCustomerAndProduct(customer.getIDCustomer(), iDProduct);
        if(cart == null)
            return false;

        cart.setQuantity(request.getQuantity());

        if(cartRepository.save(cart) == null)
            return false;

        return true;
    }

    private Customers getCustomer(String tokenHeader){
        return  iExtractToken.extractCustomer(tokenHeader);
    }

}
