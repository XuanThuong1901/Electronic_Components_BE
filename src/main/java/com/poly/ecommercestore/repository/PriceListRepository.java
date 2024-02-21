package com.poly.ecommercestore.repository;

import com.poly.ecommercestore.entity.PriceLists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceListRepository extends JpaRepository<PriceLists, Integer> {

    @Query("SELECT priceList FROM PriceLists priceList WHERE priceList.product.iDProduct = :iDProduct")
    List<PriceLists> findByProduct(@Param("iDProduct") int iDProduct);

    @Query("SELECT priceList FROM PriceLists priceList WHERE priceList.iDPrice = :iDPrice")
    PriceLists checkPriceList(@Param("iDPrice") int iDPrice);

}
