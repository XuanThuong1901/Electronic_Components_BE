package com.poly.ecommercestore.service.product;

import com.poly.ecommercestore.model.request.SpecificationRequest;
import com.poly.ecommercestore.entity.Products;
import com.poly.ecommercestore.entity.Specifications;
import com.poly.ecommercestore.repository.ProductRepository;
import com.poly.ecommercestore.repository.SpecificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecificationService implements ISpecificationService{

    private final SpecificationRepository specificationRepository;

    private final ProductRepository productRepository;

    @Override
    public List<Specifications> getByProduct(int iDProduct) {
        return specificationRepository.findByProduct(iDProduct);
    }

    @Override
    public Specifications add(SpecificationRequest request, int iDProduct) {

        if(request.getSpecificationName().equals("")){
            return null;
        }
        if(request.getParameter().equals("")){
            return null;
        }

        Specifications specifications = specificationRepository.checkSpecification(request.getIdspecification());
        if(specifications != null)
            return null;
        try {
            Products products = productRepository.findById(iDProduct).get();
            Specifications newSpecifications = new Specifications(request.getSpecificationName(), request.getParameter(), products);

            return specificationRepository.save(newSpecifications);
        }catch (Exception e){

            System.out.printf(e.toString());
            return null;
        }
    }

    @Override
    public Boolean update(SpecificationRequest specificationDTO, int idSpec) {
        if(specificationDTO.getSpecificationName().equals("")){
            return false;
        }
        if(specificationDTO.getParameter().equals("")){
            return false;
        }

        try {

            Specifications specifications = specificationRepository.findById(idSpec).get();
            specifications.setSpecificationName(specificationDTO.getSpecificationName());
            specifications.setParameter(specificationDTO.getParameter());
            specificationRepository.save(specifications);
            return true;
        }catch (Exception e){
            System.out.printf(e.toString());
            return false;
        }
    }

    @Override
    public Boolean delete(int idSpec) {
        try {
            specificationRepository.deleteById(idSpec);
            return true;
        }
        catch (Exception e){
            System.out.printf(e.toString());
            return false;
        }
    }
}
