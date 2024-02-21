package com.poly.ecommercestore.repository;

import com.poly.ecommercestore.entity.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Carts, Integer> {

    @Query("SELECT cart FROM Carts cart WHERE cart.customer.iDCustomer = :iDCustomer")
    public List<Carts> findByCustomer(@Param("iDCustomer") String iDCustomer);

    @Query("SELECT cart FROM Carts cart WHERE cart.customer.iDCustomer = :iDCustomer AND cart.product.iDProduct = :iDProduct")
    public Carts findByCustomerAndProduct(@Param("iDCustomer") String iDCustomer, @Param("iDProduct") int iDProduct);


}
