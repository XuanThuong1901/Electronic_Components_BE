package com.poly.ecommercestore.model.request;

import lombok.Data;

@Data
public class AccountRequest {

    private String email;
    private String password;
    private String newPassword;

    public AccountRequest() {
    }

    public AccountRequest(String email, String password, String newPassword) {
        this.email = (email != "") ? email :"N/A";
        this.password = (password != "") ? password :"N/A";
        this.newPassword = (newPassword != "") ? newPassword :"N/A";
    }
}
