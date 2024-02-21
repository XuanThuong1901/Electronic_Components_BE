package com.poly.ecommercestore.service.product;

import com.poly.ecommercestore.model.request.SpecificationRequest;
import com.poly.ecommercestore.entity.Specifications;

import java.util.List;

public interface ISpecificationService {
    List<Specifications> getByProduct(int iDProduct);

    Specifications add(SpecificationRequest request, int iDProduct);

    Boolean update(SpecificationRequest request, int idSpec);

    Boolean delete(int idSpec);
}
