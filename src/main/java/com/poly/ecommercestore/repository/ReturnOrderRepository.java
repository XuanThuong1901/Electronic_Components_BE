package com.poly.ecommercestore.repository;

import com.poly.ecommercestore.entity.Customers;
import com.poly.ecommercestore.entity.ReturnOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnOrderRepository extends JpaRepository<ReturnOrder, Integer> {

    List<ReturnOrder> findByCustomer(Customers customer);

}
