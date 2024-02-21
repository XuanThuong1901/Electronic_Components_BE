package com.poly.ecommercestore.repository;

import com.poly.ecommercestore.entity.ImageProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageProductRepository extends JpaRepository<ImageProducts, Integer> {

    @Query("SELECT img FROM ImageProducts img WHERE img.product.iDProduct = :IDProduct")
    List<ImageProducts> findByProduct(@Param("IDProduct") int IDProduct);

    @Query("SELECT img FROM ImageProducts img WHERE img.uRLImg = :uRLImg")
    ImageProducts findByURL(@Param("uRLImg") String uRLImg);
}
