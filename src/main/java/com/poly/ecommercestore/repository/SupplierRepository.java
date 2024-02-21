package com.poly.ecommercestore.repository;

import com.poly.ecommercestore.entity.ShippingUnits;
import com.poly.ecommercestore.entity.Suppliers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Suppliers, Integer> {
    @Query("SELECT supllier FROM Suppliers supllier WHERE supllier.supplierName = :supplierName OR supllier.email = :email OR supllier.telephone = :telephone")
    List<Suppliers> findSuppliers(@Param("supplierName") String supplierName , @Param("email") String email, @Param("telephone") String telephone);

}
