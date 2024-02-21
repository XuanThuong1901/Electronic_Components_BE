package com.poly.ecommercestore.model.response;

import com.poly.ecommercestore.entity.Accounts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Accounts account;
    private String message;

}
