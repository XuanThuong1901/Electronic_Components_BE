package com.poly.ecommercestore.service.shippingunit;

import com.poly.ecommercestore.entity.ShippingUnits;
import com.poly.ecommercestore.model.request.ShippingUnitRequest;

import java.util.List;

public interface IShippingUnitService {

    List<ShippingUnits> getAllShippingUnit();

    Boolean addShippingUnit(String tokenHeader, ShippingUnitRequest shippingUnit);

    Boolean updateShippingUnit(String tokenHeader, ShippingUnitRequest shippingUnit, int iDShippingUnit);

    Boolean deleteShippingUnit(int iDShippingUnit);
}
