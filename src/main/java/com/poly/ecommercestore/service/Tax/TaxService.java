package com.poly.ecommercestore.service.Tax;

import com.poly.ecommercestore.entity.Tax;
import com.poly.ecommercestore.repository.TaxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxService implements ITaxService{
    private final TaxRepository taxRepository;

    @Override
    public List<Tax> getAllTax() {
        return taxRepository.findAll();
    }
}
