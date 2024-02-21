package com.poly.ecommercestore.service.supplier;

import com.poly.ecommercestore.entity.Accounts;
import com.poly.ecommercestore.entity.Suppliers;
import com.poly.ecommercestore.repository.AccountRepository;
import com.poly.ecommercestore.repository.SupplierRepository;
import com.poly.ecommercestore.model.request.SupplierRequest;
import com.poly.ecommercestore.util.extractToken.IExtractToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService implements ISupplierService{

    private final IExtractToken iExtractToken;
    private final SupplierRepository supplierRepository;

    @Override
    public Boolean addSupplier(String tokenHeader, SupplierRequest request) {
        Accounts user = iExtractToken.extractAccount(tokenHeader);
        if(user == null || user.getRole().getIDRole() != 2)
            return false;

        if(supplierRepository.findSuppliers(request.getSupplierName(), request.getEmail(), request.getTelephone()).size() != 0)
            return false;

        Suppliers newSupplier = new Suppliers(request.getSupplierName(), request.getEmail(), request.getTelephone(), request.getAddress());

        supplierRepository.save(newSupplier);

        return true;
    }

    @Override
    public List<Suppliers> getAllSupplier() {
        return supplierRepository.findAll();
    }

    @Override
    public Boolean updateSupplier(String tokenHeader, SupplierRequest request, int iDSupplier) {

        Accounts user = iExtractToken.extractAccount(tokenHeader);
        if(user == null || user.getRole().getIDRole() != 2)
            return false;
        Suppliers updateSupplier = supplierRepository.getReferenceById(iDSupplier);
        if(updateSupplier == null)
            return false;

//        updateSupplier.setSupplierName(supplier.getSupplierName());
//        updateSupplier.setEmail(supplier.getEmail());
//        updateSupplier.setTelephone(supplier.getTelephone());
        updateSupplier.setAddress(request.getAddress());

        supplierRepository.save(updateSupplier);

        return true;
    }

    @Override
    public Boolean deleteSupplier(int iDSupplier) {

        Suppliers supplier = supplierRepository.findById(iDSupplier).get();
        if(supplier == null || supplier.getProducts().size() != 0)
            return false;

        supplierRepository.delete(supplier);
        return true;
    }
}
