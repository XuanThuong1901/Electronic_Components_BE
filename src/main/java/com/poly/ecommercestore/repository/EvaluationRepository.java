package com.poly.ecommercestore.repository;

import com.poly.ecommercestore.entity.Evaluations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluations, Integer> {

    @Query("SELECT evaluation FROM Evaluations evaluation WHERE evaluation.product.iDProduct = :iDProduct")
    List<Evaluations> findByProduct(@Param("iDProduct") int iDProduct);
}
