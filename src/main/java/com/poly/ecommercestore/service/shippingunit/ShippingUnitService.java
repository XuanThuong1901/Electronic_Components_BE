package com.poly.ecommercestore.service.shippingunit;

import com.poly.ecommercestore.entity.Accounts;
import com.poly.ecommercestore.entity.ShippingUnits;
import com.poly.ecommercestore.repository.ShippingUnitRepository;
import com.poly.ecommercestore.model.request.ShippingUnitRequest;
import com.poly.ecommercestore.util.extractToken.IExtractToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingUnitService implements IShippingUnitService{

    private final IExtractToken iExtractToken;
    private final ShippingUnitRepository shippingUnitRepository;

    @Override
    public List<ShippingUnits> getAllShippingUnit() {
        return shippingUnitRepository.findAll();
    }

    @Override
    public Boolean addShippingUnit(String tokenHeader, ShippingUnitRequest request) {
        Accounts user = iExtractToken.extractAccount(tokenHeader);
        if(user == null || user.getRole().getIDRole() != 2)
            return false;

        if(shippingUnitRepository.findShippingUnits(request.getShippingUnitName(), request.getEmail(), request.getTelephone()).size() != 0)
            return false;
        System.out.println("11");

        ShippingUnits newShippingUnit = new ShippingUnits(request.getShippingUnitName(), request.getEmail(), request.getTelephone(), request.getAddress());
        shippingUnitRepository.save(newShippingUnit);

        return true;
    }

    @Override
    public Boolean updateShippingUnit(String tokenHeader, ShippingUnitRequest request, int iDShippingUnit) {

        Accounts account = iExtractToken.extractAccount(tokenHeader);
        if(account == null || account.getRole().getIDRole() != 2)
            return false;

        ShippingUnits updateShippingUnit = shippingUnitRepository.getReferenceById(iDShippingUnit);

        if(updateShippingUnit == null)
            return false;


        updateShippingUnit.setAddress(request.getAddress());

        if (shippingUnitRepository.save(updateShippingUnit) == null)
            return false;

        return true;
    }

    @Override
    public Boolean deleteShippingUnit(int iDShippingUnit) {

        ShippingUnits shippingUnit = shippingUnitRepository.getReferenceById(iDShippingUnit);

        if(shippingUnit == null || shippingUnit.getOrders().size() != 0)
            return false;

        shippingUnitRepository.delete(shippingUnit);
        return true;
    }
}
