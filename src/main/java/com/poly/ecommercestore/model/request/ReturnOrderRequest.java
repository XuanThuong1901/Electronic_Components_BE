package com.poly.ecommercestore.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnOrderRequest {
    private String name;
    private Integer productId;
    private String address;
    private String phoneNumber;
    private Integer quantity;
    private BigDecimal amount;
    private String reason;
    private String formality;
}
