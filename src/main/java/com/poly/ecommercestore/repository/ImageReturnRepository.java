package com.poly.ecommercestore.repository;

import com.poly.ecommercestore.entity.ImageReturnOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageReturnRepository extends JpaRepository<ImageReturnOrder, Integer> {
}
