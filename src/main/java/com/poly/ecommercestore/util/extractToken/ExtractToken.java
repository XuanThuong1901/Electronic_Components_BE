package com.poly.ecommercestore.util.extractToken;

import com.poly.ecommercestore.configuration.jwt.JwtService;
import com.poly.ecommercestore.entity.Accounts;
import com.poly.ecommercestore.entity.Customers;
import com.poly.ecommercestore.entity.Employers;
import com.poly.ecommercestore.repository.AccountRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtractToken implements IExtractToken{
    private final JwtService jwtService;
    private final AccountRepository accountRepository;

    @Override
    public String extractRole(String token) {
        Accounts account = extractAccount(token);
        return account.getRole().getRole();
    }


    @Override
    public Accounts extractAccount(String token) {
        String username = jwtService.extractUserName(token);
        Accounts account = accountRepository.findByEmail(username).orElse(null);
        return account;
    }

    @Override
    public Customers extractCustomer(String token) {
        Accounts account = extractAccount(token);

        return account.getCustomers();
    }

    @Override
    public Employers extractEmployee(String token) {
        Accounts account = extractAccount(token);
        return account.getEmployer();
    }

}
