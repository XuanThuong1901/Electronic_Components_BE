package com.poly.ecommercestore.service.supplier;

import com.poly.ecommercestore.entity.Suppliers;
import com.poly.ecommercestore.model.request.SupplierRequest;

import java.util.List;

public interface ISupplierService {

    Boolean addSupplier(String tokenHeader, SupplierRequest supplier);

    List<Suppliers> getAllSupplier();

    Boolean updateSupplier(String tokenHeader, SupplierRequest supplier, int iDSupplier);

    Boolean deleteSupplier(int iDSupplier);
}
