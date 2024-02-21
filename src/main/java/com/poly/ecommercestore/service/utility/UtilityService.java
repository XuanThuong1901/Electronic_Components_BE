package com.poly.ecommercestore.service.utility;

import com.poly.ecommercestore.entity.Customers;
import com.poly.ecommercestore.entity.Employers;
import com.poly.ecommercestore.repository.CustomerRepository;
import com.poly.ecommercestore.repository.EmployerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilityService implements IUtilityService{

    private final CustomerRepository customerRepository;
    private final EmployerRepository employerRepository;

    @Override
    public List<Customers> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public List<Employers> getAllEmployee() {
        return employerRepository.findAll();
    }

    @Override
    public Employers getEmployee(String tokenHeader, String id) {
        return employerRepository.findById(id).get();
    }
}
