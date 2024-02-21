package com.poly.ecommercestore.service.order;

import com.poly.ecommercestore.entity.Orders;
import com.poly.ecommercestore.model.request.OrderRequest;

import java.util.List;

public interface IOrderService {

    Orders getOneOrder(String tokenHeader, int iDOrder);

    List<Orders> getOrderByCustomer(String tokenHeader);

    List<Orders> getAllOrder(String tokenHeader);

    int addOrder(String tokenHeader , OrderRequest request);

    Boolean statusConfirmedOrder(String tokenHeader, int iDOrder);

    Boolean statusDeliveryOrder(String tokenHeader, int iDOrder);

    Boolean statusDeliveredOrder(String tokenHeader, int iDOrder);

    Boolean statusCanceledOrder(String tokenHeader, int iDOrder);
}
