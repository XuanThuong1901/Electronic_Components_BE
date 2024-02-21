package com.poly.ecommercestore.repository;

import com.poly.ecommercestore.entity.ImportStocks;
import com.poly.ecommercestore.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

@Repository
public interface ImportStockRepository extends JpaRepository<ImportStocks, Integer> {

    @Query("SELECT import FROM ImportStocks import WHERE import.dateAdded BETWEEN :startDay AND :endDay")
    List<ImportStocks> findByStartEndDay(Date startDay, Date endDay);
}
