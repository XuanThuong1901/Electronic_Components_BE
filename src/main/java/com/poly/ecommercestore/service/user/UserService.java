package com.poly.ecommercestore.service.user;

import com.poly.ecommercestore.entity.*;
import com.poly.ecommercestore.model.request.AccountRequest;
import com.poly.ecommercestore.repository.*;
import com.poly.ecommercestore.model.request.UserRequest;
import com.poly.ecommercestore.util.extractToken.IExtractToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements  IUserService{

    private final IExtractToken iExtractToken;
    private final AccountRepository accountRepository;
    private final StatusRepository statusRepository;
    private final CustomerRepository customerRepository;
    private final EmployerRepository employerRepository;

    @Override
    public Accounts getUser(String tokenHeader) {
        Accounts account = iExtractToken.extractAccount(tokenHeader);
//        if(account.getCustomers() != null)
//            return account.getCustomers();

        return account;
    }

    @Override
    public Boolean updateUser(String tokenHeader, String idAccount, UserRequest request) {

        Accounts user = iExtractToken.extractAccount(tokenHeader);

        try {
            if(user.getCustomers() != null){
                Customers updateCus = user.getCustomers();
                updateCus.setName(request.getName());
                updateCus.setAddress(request.getAddress());
                updateCus.setTelephone(request.getTelephone());

                customerRepository.save(updateCus);

                return true;
            }
            else{
                Accounts updateAccount = accountRepository.findById(idAccount).get();
                Employers updateEmp = updateAccount.getEmployer();
                updateEmp.setName(request.getName());
                updateEmp.setAddress(request.getAddress());
                updateEmp.setTelephone(request.getTelephone());
                updateEmp.setBirthday(request.getBirthday());
                updateEmp.setGender(request.isGender());
                updateEmp.setIdentityCard(request.getIdentityCard());
                employerRepository.save(updateEmp);

                return true;
            }
        }
        catch (Exception e){
            System.out.printf(e.toString());
            return false;
        }
    }

    @Override
    public Boolean updatePassword(String tokenHeader, AccountRequest request) {
        try {
            Accounts updateAccount = iExtractToken.extractAccount(tokenHeader);
            if(updateAccount != null && updateAccount.getPassword().equals(request.getPassword())){
                updateAccount.setPassword(request.getNewPassword());
                accountRepository.save(updateAccount);
                return true;
            }
            return false;
        }
        catch (Exception ex){
            System.out.println(ex.toString());
            return false;
        }
    }

    @Override
    public Boolean setStatusUser(String tokenHeader, String idAccount, int idStatus) {
        Accounts accountAdmin = iExtractToken.extractAccount(tokenHeader);
        Accounts user = accountRepository.getById(idAccount);
        if(accountAdmin == null || user == null || accountAdmin.getIDAccount() == user.getIDAccount()){
            return false;
        }
        Status status = statusRepository.findById(idStatus).get();
        user.setStatus(status);
        accountRepository.save(user);
        return true;
    }
}
