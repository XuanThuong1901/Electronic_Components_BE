package com.poly.ecommercestore.service.product;

import com.poly.ecommercestore.entity.PriceLists;
import com.poly.ecommercestore.model.request.PriceListRequest;

import java.util.List;

public interface IPriceListService {

    List<PriceLists> getPriceListByProduct(int iDProduct);

    PriceLists addPriceList(PriceListRequest request, String iDEmployer, int iDProduct);

    Boolean updateStatusPriceList(int iDProduct);
}
