package com.poly.ecommercestore.repository;

import com.poly.ecommercestore.entity.DetailImportStocks;
import com.poly.ecommercestore.entity.DetailOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetailOrderRepository extends JpaRepository<DetailOrders, Integer> {

}
