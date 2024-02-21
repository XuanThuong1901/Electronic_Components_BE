package com.poly.ecommercestore.service.product;

import com.poly.ecommercestore.model.request.PriceListRequest;
import com.poly.ecommercestore.entity.Employers;
import com.poly.ecommercestore.entity.PriceLists;
import com.poly.ecommercestore.entity.Products;
import com.poly.ecommercestore.repository.EmployerRepository;
import com.poly.ecommercestore.repository.PriceListRepository;
import com.poly.ecommercestore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PriceListService implements IPriceListService{

    @Autowired
    private PriceListRepository priceListRepository;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<PriceLists> getPriceListByProduct(int iDProduct) {
        return priceListRepository.findByProduct(iDProduct);
    }

    @Override
    public PriceLists addPriceList(PriceListRequest request, String iDEmployer, int iDProduct) {

        Employers employer = employerRepository.findById(iDEmployer).orElse(null);
        Products product = productRepository.findById(iDProduct).get();
        PriceLists priceList = priceListRepository.checkPriceList(request.getIdprice());
        if(employer == null || product == null || priceList != null)
            return null;

        PriceLists newPriceList = new PriceLists();
        newPriceList.setEmployer(employer);
        newPriceList.setProduct(product);
        newPriceList.setUpdateDate(new Date());
        newPriceList.setType(request.getType());
//        newPriceList.setApplicableDate(priceList.getApplicableDate());
        newPriceList.setApplicableDate(new Date());

        newPriceList.setPrice(request.getPrice());
        newPriceList.setStatus(true);

        priceListRepository.save(newPriceList);

        return newPriceList;
    }

    @Override
    public Boolean updateStatusPriceList(int iDProduct) {
        return null;
    }
}
