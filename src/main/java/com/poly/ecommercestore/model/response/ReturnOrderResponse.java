package com.poly.ecommercestore.model.response;

import com.poly.ecommercestore.entity.ReturnOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnOrderResponse {
    private List<ReturnOrder> returnOrderList;
    private String message;
}
