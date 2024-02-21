package com.poly.ecommercestore.repository;

import com.poly.ecommercestore.entity.Specifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecificationRepository extends JpaRepository<Specifications, Integer> {

    @Query("SELECT specification FROM Specifications specification WHERE specification.product.iDProduct = :iDProduct")
    List<Specifications> findByProduct(@Param("iDProduct") int iDProduct);

    @Query("SELECT specification FROM Specifications specification WHERE specification.iDSpecification = :iDSpecification")
    Specifications checkSpecification(@Param("iDSpecification") int iDSpecification);
}
