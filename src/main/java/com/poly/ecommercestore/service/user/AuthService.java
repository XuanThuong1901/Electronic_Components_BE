package com.poly.ecommercestore.service.user;

import com.poly.ecommercestore.util.Message;
import com.poly.ecommercestore.configuration.jwt.JwtService;
import com.poly.ecommercestore.entity.*;
import com.poly.ecommercestore.model.request.AccountRequest;
import com.poly.ecommercestore.model.response.AuthResponse;
import com.poly.ecommercestore.repository.*;
import com.poly.ecommercestore.model.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService{

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final StatusRepository statusRepository;
    private final RoleRepository roleRepository;
    private final CustomerRepository customerRepository;
    private final EmployerRepository employerRepository;

    private static final int lengthID = 10;
    private static final int iDStatus = 1;
    private static final String UPLOAD_DIR = "D:\\NAM4-HK3\\THUCTAPTOTNGHIEP\\ecommercestore-web-customer\\src\\assets\\images\\avatar";


    @Override
    public AuthResponse login(AccountRequest request) {

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            var account = accountRepository.findByEmail(request.getEmail()).orElse(null);

            System.out.println(account.getAuthorities());

            if(account == null)
            {
                return AuthResponse.builder()
                        .message(Message.LOGIN_ERROR001)
                        .build();
            }

            var jwtToken = jwtService.generateToken(account);

            AuthResponse response =  AuthResponse.builder()
                    .account(account)
                    .token(jwtToken)
                    .message(Message.LOGIN_SUCCESS)
                    .build();

            return response;

        }catch (Exception e){
            System.out.printf(e.toString());
            return AuthResponse.builder()
                    .message(Message.LOGIN_ERROR001)
                    .build();
        }
    }

    @Override
    public boolean registerCustomer(UserRequest request) {
        try {
            Accounts newAccount = new Accounts();
            Status status = statusRepository.findById(iDStatus).orElse(null);
            Roles role = roleRepository.findById(1).get();
//        String encoderPassword = passwordEncoder.encode(user.getPassword());

            newAccount.setEmail(request.getEmail());
            newAccount.setStatus(status);
            newAccount.setRole(role);
            String newID = "CUS" + generateRandomString();
            newAccount.setIDAccount(newID);
            newAccount.setPassword(passwordEncoder.encode(request.getPassword()));

            Customers newCustomer = new Customers(newID, newAccount, request.getName(), request.getAddress(), request.getTelephone());

            newAccount.setCustomers(newCustomer);
            this.accountRepository.save(newAccount);
            this.customerRepository.save(newCustomer);

            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean registerEmployee(UserRequest request, MultipartFile avatar) {
        try {
            Accounts newAccount = new Accounts();
            Status status = statusRepository.findById(iDStatus).orElse(null);
            Roles role = roleRepository.findById(request.getRole()).orElse(null);
//        String encoderPassword = passwordEncoder.encode(user.getPassword());

            newAccount.setEmail(request.getEmail());
            newAccount.setStatus(status);
            newAccount.setRole(role);

            String newID = "EMP" + generateRandomString();
            newAccount.setIDAccount(newID);
            Random random = new Random();
//            int newPassword = random.nextInt(999999 - 100000 + 1) + 100000;
            newAccount.setPassword(passwordEncoder.encode("123456"));
            String newAvatar = saveImage(avatar);
            Employers newEmployer = new Employers(newID, newAccount,request.getName(), request.getAddress(), request.getBirthday(), request.isGender(), request.getTelephone(), request.getIdentityCard(), "avatar3.jpg");


            newAccount.setEmployer(newEmployer);
            this.accountRepository.save(newAccount);
            this.employerRepository.save(newEmployer);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean resetPassword(String email) {
        Accounts account = accountRepository.findByEmail(email).orElse(null);
        if(account == null)
            return false;
        Random random = new Random();
        int newPassword = random.nextInt(999999 - 100000 + 1) + 100000;
        account.setPassword(Integer.toString(newPassword));
        accountRepository.save(account);
        return true;
    }

    public static String generateRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < lengthID; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    private String saveImage(MultipartFile image) {
        try {
            String fileName = image.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            Files.write(filePath, image.getBytes());
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
