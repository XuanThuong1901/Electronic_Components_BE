package com.poly.ecommercestore.repository;

import com.poly.ecommercestore.entity.ShippingUnits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingUnitRepository extends JpaRepository<ShippingUnits, Integer> {

    @Query("SELECT ship FROM ShippingUnits ship WHERE ship.shippingUnitName = :shippingUnitName OR ship.email = :email OR ship.telephone = :telephone")
    List<ShippingUnits> findShippingUnits(@Param("shippingUnitName") String shippingUnitName , @Param("email") String email, @Param("telephone") String telephone);

}
